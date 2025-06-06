<template>
	<div>
		<div class="artplayer-app"></div>
		<div
			v-if="currentVideo && common.isMobile.value"
			class="juyo-video-switch-tools"
		>
			<el-button
				:disabled="!getPrevAndNextVideo(currentVideo.name).prev"
				:icon="IconPrev"
				@click="playPrevVideo"
				>上一个视频</el-button
			>
			<el-button
				:disabled="!getPrevAndNextVideo(currentVideo.name).next"
				:icon="IconNext"
				@click="playNextVideo"
				>下一个视频</el-button
			>
		</div>
	</div>
</template>

<script setup>
import Artplayer from 'artplayer'
let common = useCommon()
let route = useRoute()

import useFilePwd from '@/composables/file/useFilePwd'
let { getPathPwd } = useFilePwd()

import useRouterData from '@/composables/useRouterData'
let { currentPath, storageKey } = useRouterData()

import { loadFileItemReq } from '@/api/home'

import IconPrev from '~icons/custom/prev'
import IconNext from '~icons/custom/next'

import useFileDataStore from '@/stores/file-data'
let fileDataStore = useFileDataStore()

import useStorageConfigStore from '@/stores/storage-config'
let storageConfigStore = useStorageConfigStore()

// 获取视频列表, 如果是当前视频, 则设置为默认勾选.
const getVideoList = (currentName) => {
	let result = []
	fileDataStore.filterFileByType('video').forEach((file) => {
		result.push({
			default: file.name === currentName,
			html: file.name,
			url: file.url,
		})
	})

	return result
}

let currentVideo = ref(null)


// 获取上一个和下一个视频, 没有则返回空
const getPrevAndNextVideo = (currentName) => {
	let videoList = fileDataStore.filterFileByType('video')

	clearExceedProgress()
	for (let i = 0; i < videoList.length; i++) {
		let videoItem = videoList[i]
		if (videoItem.name === currentName) {
			return {
				prev: videoList[i - 1] || null,
				next: videoList[i + 1] || null,
			}
		}
	}
}

// 清楚超过进度的视频缓存, 防止再次播放时直接结束.
const clearExceedProgress = () => {
	let playProgress = JSON.parse(
		localStorage.getItem('_h5_player_play_progress_')
	)
	if (playProgress) {
		for (let key of Object.keys(playProgress)) {
			if (key.endsWith(playProgress[key].progress)) {
				delete playProgress[key]
			}
		}
		localStorage.setItem(
			'_h5_player_play_progress_',
			JSON.stringify(playProgress)
		)
	}
}

// 字幕列表, 如果是当前视频, 则设置为默认勾选.
const getSubtitles = (currentName) => {
	let subtitleList = []
	fileDataStore.fileList.find((item, index) => {
		let name = item.name.toLowerCase()
		if (
			name === currentName + '.vtt' ||
			name === currentName + '.srt' ||
			name === currentName + '.ass'
		) {
			subtitleList.push({
				default: subtitleList.length === 0,
				url: item.url,
				html: name,
			})
		}
	})

	if (subtitleList.length > 0) {
		subtitleList.push({
			url: '',
			html: '关闭字幕',
		})
	}

	return subtitleList
}

let hiddenIcon =
	'<i class="art-icon art-icon-not-hidden"><svg t="1662978336444" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="968" width="128" height="128"><path d="M711.456 377.184c32.736 31.52 60.928 72.256 84.64 122.208a40.64 40.64 0 0 1 0 34.72c-63.776 135.04-160.48 202.528-290.112 202.528-46.752 0-89.248-8.832-127.456-26.464l37.184-37.184c27.52 10.048 57.6 15.104 90.272 15.104 108.864 0 188.48-55.168 244.608-171.296-21.088-43.584-45.44-78.56-73.44-105.28l34.304-34.336z m13.024-122.816l28.768 28.8a5.376 5.376 0 0 1 0 7.616L272.96 771.04a5.408 5.408 0 0 1-7.648 0l-28.8-28.8a5.376 5.376 0 0 1 0-7.616l71.36-71.36c-35.84-32.384-66.56-75.392-92.032-129.088a40.64 40.64 0 0 1 0-34.72C279.68 364.48 376.384 296.96 505.984 296.96c50.752 0 96.448 10.4 137.12 31.168l73.76-73.728a5.376 5.376 0 0 1 7.616 0z m-218.496 91.136c-108.8 0-188.416 55.168-244.608 171.296 22.944 47.36 49.792 84.608 80.928 112.096l56.256-56.256a118.688 118.688 0 0 1 160.576-160.576l47.424-47.424c-30.336-12.832-63.776-19.136-100.576-19.136z m-70.016 137.088a75.616 75.616 0 0 0-4.64 57.28l95.04-95.04a75.616 75.616 0 0 0-90.4 37.76z m60.416 109.504a75.456 75.456 0 0 0 82.144-82.112l35.616-35.616a118.72 118.72 0 0 1-153.376 153.344l35.616-35.616z" p-id="969"></path></svg></i>'
let notHiddenIcon =
	'<i class="art-icon art-icon-hidden"><svg t="1662978261880" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4451" width="128" height="128"><path d="M512.032 648a136.128 136.128 0 0 1-136-136c0-74.976 60.992-136 136-136s136 61.024 136 136a136.128 136.128 0 0 1-136 136z m0-224c-48.544 0-88 39.456-88 88s39.456 88 88 88 88-39.456 88-88-39.456-88-88-88z" p-id="4452"></path><path d="M512.032 743.616a327.68 327.68 0 0 1-231.872-95.616L142.88 512l137.28-136a327.68 327.68 0 0 1 231.872-95.584c87.328 0 169.664 33.952 231.872 95.584l137.28 136-137.28 136a327.52 327.52 0 0 1-231.872 95.616zM206.528 512l107.424 101.952c53.152 52.672 123.488 81.696 198.048 81.696s144.896-29.024 198.08-81.696L817.536 512l-107.424-101.952c-53.152-52.672-123.488-81.696-198.048-81.696s-144.896 29.024-198.08 81.696L206.528 512z" p-id="4453"></path></svg></i>'
let videoListIcon =
	'<i class="art-icon art-icon-video-list"><svg t="1650551038453" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="12028" width="20" height="20"><path d="M111.395066 179.64038l801.208844 0 0 87.866187-801.208844 0 0-87.866187Z" p-id="12029"></path><path d="M111.395066 468.067418l801.208844 0 0 87.866187-801.208844 0 0-87.866187Z" p-id="12030"></path><path d="M111.395066 756.493433l801.208844 0 0 87.866187-801.208844 0 0-87.866187Z" p-id="12031"></path></svg></i>'
let subtitleIcon =
	'<i class="art-icon art-icon-subtitle"><svg xmlns="http://www.w3.org/2000/svg" height="22" width="22" viewBox="0 0 48 48">\n' +
	'    <path d="M0 0h48v48H0z" fill="none"></path>\n' +
	'    <path d="M40 8H8c-2.21 0-4 1.79-4 4v24c0 2.21 1.79 4 4 4h32c2.21 0 4-1.79 4-4V12c0-2.21-1.79-4-4-4zM8 24h8v4H8v-4zm20 12H8v-4h20v4zm12 0h-8v-4h8v4zm0-8H20v-4h20v4z"></path>\n' +
	'</svg></i>'

let nextVideoIcon =
	'<i class="art-icon art-icon-next-video"><svg height="25" width="25"  viewBox="0 0 22 22"><path d="M16 5a1 1 0 00-1 1v4.615a1.431 1.431 0 00-.615-.829L7.21 5.23A1.439 1.439 0 005 6.445v9.11a1.44 1.44 0 002.21 1.215l7.175-4.555a1.436 1.436 0 00.616-.828V16a1 1 0 002 0V6C17 5.448 16.552 5 16 5z"></path></svg></i>'
let prevVideoIcon =
	'<i class="art-icon art-icon-prev-video" style="transform: scale(-1,1);"><svg height="25" width="25"  viewBox="0 0 22 22"><path d="M16 5a1 1 0 00-1 1v4.615a1.431 1.431 0 00-.615-.829L7.21 5.23A1.439 1.439 0 005 6.445v9.11a1.44 1.44 0 002.21 1.215l7.175-4.555a1.436 1.436 0 00.616-.828V16a1 1 0 002 0V6C17 5.448 16.552 5 16 5z"></path></svg></i>'

import flvjs from 'flv.js'
import Hls from 'hls.js'

const autoPlayNextVideo = useStorage('juyo-video-auto-player-next', false)
const autoPlayVideo = useStorage('juyo-video-auto-player', false)
const hiddenTools = useStorage('juyo-video-hiddle-tools', false)

// //images
// import videoDownloadImg from '@/assets/images/video-download.png'
// import videoThunderImg from '@/assets/images/video-thunder.png'
// import videoMotrixImg from '@/assets/images/video-motrix.png'
// import videoPotplayerImg from '@/assets/images/video-potplayer.png'
// import videoIinaImg from '@/assets/images/video-iina.png'
// import videoVlcImg from '@/assets/images/video-vlc.png'
// import videoNplayerImg from '@/assets/images/video-nplayer.png'
// import videoMxplayerImg from '@/assets/images/video-mxplayer.png'
// import videoMxplayerProImg from '@/assets/images/video-mxplayer-pro.png'

let art = null
const initArtPlayer = async (name, url) => {
	currentVideo.value = {
		name,
		url,
	}
	document.querySelector('.juyo-video-dialog .el-dialog__title').innerHTML = "视频播放：" + name
	if (art) {
		art.destroy()
	}

	let videoType = 'mp4'

	if (name.toLowerCase().endsWith('flv')) {
		videoType = 'flv'
	} else if (name.toLowerCase().endsWith('m3u8')) {
		videoType = 'm3u8'
	}

	// 兼容 h5ai_dplayer https://github.com/Pearlulu/h5ai_dplayer
	// 取 __{video_name}__/video.m3u8 完整路径
	let originPathAndName = common.removeDuplicateSeparator(
		`${currentPath.value}/${name}`
	)
	let m3u8PathAndName = common.removeDuplicateSeparator(
		`${currentPath.value}/__${name}__/video.m3u8`
	)

	let m3u8FileItemParam = {
		storageKey: storageKey.value,
		path: m3u8PathAndName,
		password: getPathPwd(),
	}

	let h5aiDplayerMode = false

	let fileItemResult = await loadFileItemReq(m3u8FileItemParam)

	if (fileItemResult?.data?.code === 0) {
		console.log(
			'检测到当前为 h5ai_dplayer 兼容模式, 替换播放文件为: ' + m3u8PathAndName
		)
		videoType = 'm3u8'
		h5aiDplayerMode = true
	}

	if (videoType === 'm3u8') {
		// 取 __{video_name}__/video.m3u8 直链地址

		let m3u8Link = common.removeDuplicateSeparator(
			storageConfigStore.globalConfig.domain +
				'/' +
				storageConfigStore.globalConfig.directLinkPrefix +
				'/' +
				storageKey.value +
				'/' +
				(h5aiDplayerMode ? m3u8PathAndName : originPathAndName)
		)

		currentVideo.value.url = m3u8Link
		console.log(
			'检测到当前播放的文件为 m3u8, 为了正常加载 ts 文件, 替换 m3u8 为直链: ' +
				m3u8Link
		)
	}

	let options = {
		container: '.artplayer-app',
		title: name,
		url: currentVideo.value.url,
		type: videoType,
		setting: true,
		playbackRate: true,
		flip: true,
		fullscreen: true,
		fastForward: true,
		autoOrientation: true,
		aspectRatio: true,
		fullscreenWeb: true,
		theme: '#23ade5',
		lock: true,
		subtitleOffset: true,
		miniProgressBar: true,
		autoplay: autoPlayVideo.value,
		whitelist: ['*'],
		moreVideoAttr: {
			'x5-video-player-type': 'h5',
			'x5-video-player-fullscreen': false,
			'x5-video-orientation': 'portraint',
			preload: 'metadata',
			crossOrigin: 'anonymous',
		},
		customType: {
			flv: function (video, url) {
				if (flvjs.isSupported()) {
					const flvPlayer = flvjs.createPlayer({
						type: 'flv',
						url: url,
					})
					flvPlayer.attachMediaElement(video)
					flvPlayer.load()
				} else {
					art.notice.show = '不支持播放格式：flv'
				}
			},
			m3u8: function (video, url) {
				if (Hls.isSupported()) {
					const hls = new Hls()
					hls.loadSource(url)
					hls.attachMedia(video)
				} else {
					const canPlay = video.canPlayType('application/vnd.apple.mpegurl')
					if (canPlay === 'probably' || canPlay === 'maybe') {
						video.src = url
					} else {
						art.notice.show = '不支持播放格式：m3u8'
					}
				}
			},
		},
		contextmenu: [
			{
				html: '下载',
				click: function () {
					window.open(url)
				},
			},
		],
		settings: [
			{
				html: '自动播放',
				tooltip: autoPlayVideo.value ? '开启' : '关闭',
				icon: '<img width="22" heigth="22" src="data:image/svg+xml;base64,Cjxzdmcgdmlld0JveD0iMCAwIDgwIDgwIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48ZGVmcz48cGF0aCBpZD0icGlkLTY0LXN2Z28tYSIgZD0iTTAgMGg4MHY4MEgweiI+PC9wYXRoPjxwYXRoIGQ9Ik01Mi41NDYgOC4wMTRhMy45OTggMy45OTggMCAwMTQuMjIyIDMuMDc3Yy4xMDQuNDQ2LjA5My44MDguMDM5IDEuMTM4YTIuNzQgMi43NCAwIDAxLS4zMTIuODgxYy0uMDczLjEzMi0uMTYuMjU0LS4yNDYuMzc2bC0uMjU3LjM2Ni0uNTIxLjczYy0uNy45NjktMS40MTUgMS45MjYtMi4xNTQgMi44NjZsLS4wMTUuMDJhMjQwLjk0NSAyNDAuOTQ1IDAgMDE1Ljk4Ni4zNDFsMS42NDMuMTIzLjgyMi4wNjYuNDEuMDM0LjIwNi4wMTguMTAzLjAwOC4xMTUuMDEyYzEuMjY2LjExNiAyLjUxNi40NSAzLjY3Ny45NzVhMTEuNjYzIDExLjY2MyAwIDAxMy4xNjYgMi4xMTRjLjkzMS44NyAxLjcxOSAxLjg5NSAyLjMyMSAzLjAyMmExMS41OTUgMTEuNTk1IDAgMDExLjIyNCAzLjYxM2MuMDMuMTU3LjA0Ni4zMTYuMDY4LjQ3NGwuMDE1LjExOS4wMTMuMTEyLjAyMi4yMDYuMDg1LjgyMi4xNTkgMS42NDZjLjEgMS4wOTguMTkgMi4xOTguMjcgMy4yOTguMzE1IDQuNC40NjMgOC44MjkuMzYgMTMuMjU1YTE2Ni40ODkgMTY2LjQ4OSAwIDAxLS44NDMgMTMuMjEzYy0uMDEyLjEyNy0uMDM0LjI5Ny0uMDUzLjQ1NGE3LjU4OSA3LjU4OSAwIDAxLS4wNzIuNDc1bC0uMDQuMjM3LS4wNS4yMzZhMTEuNzYyIDExLjc2MiAwIDAxLS43NCAyLjI4NyAxMS43NTUgMTEuNzU1IDAgMDEtNS4xMTggNS41NyAxMS43MDUgMTEuNzA1IDAgMDEtMy42MjMgMS4yNjNjLS4xNTguMDI0LS4zMTYuMDUyLS40NzUuMDcybC0uNDc3LjA1My0uODIxLjA3MS0xLjY0NC4xMzRjLTEuMDk2LjA4Ni0yLjE5Mi4xNi0zLjI4OC4yM2EyNjAuMDggMjYwLjA4IDAgMDEtNi41NzguMzI1Yy04Ljc3Mi4zMjQtMTcuNTQ2LjIyLTI2LjMxMy0uMzAyYTI0Mi40NTggMjQyLjQ1OCAwIDAxLTMuMjg3LS4yMmwtMS42NDMtLjEyOS0uODIyLS4wNjktLjQxLS4wMzUtLjIwNi0uMDE4Yy0uMDY4LS4wMDYtLjEzMy0uMDEtLjIxOC0uMDJhMTEuNTY2IDExLjU2NiAwIDAxLTMuNy0uOTkyIDExLjczMiAxMS43MzIgMCAwMS01LjQ5Ny01LjE3OCAxMS43MyAxMS43MyAwIDAxLTEuMjE1LTMuNjI3Yy0uMDI0LS4xNTgtLjA1MS0uMzE2LS4wNjctLjQ3NWwtLjAyNi0uMjM4LS4wMTMtLjExOS0uMDEtLjEwMy0uMDctLjgyMy0uMTMyLTEuNjQ4YTE5MC42MzcgMTkwLjYzNyAwIDAxLS4yMi0zLjI5OGMtLjI1Ni00LjM5OS0uMzU4LTguODE3LS4yNTgtMTMuMjMzLjA5OS00LjQxMi4zNzItOC44MTEuNzg4LTEzLjE5N2ExMS42NSAxMS42NSAwIDAxMy4wMzktNi44MzUgMTEuNTg1IDExLjU4NSAwIDAxNi41NzItMy41NjNjLjE1Ny0uMDIzLjMxMi0uMDUxLjQ3LS4wN2wuNDctLjA1LjgyLS4wNyAxLjY0My0uMTNhMjI4LjQ5MyAyMjguNDkzIDAgMDE2LjY0Ny0uNDA1bC0uMDQxLS4wNWE4OC4xNDUgODguMTQ1IDAgMDEtMi4xNTQtMi44NjdsLS41Mi0uNzMtLjI1OC0uMzY2Yy0uMDg2LS4xMjItLjE3My0uMjQ0LS4yNDYtLjM3NmEyLjc0IDIuNzQgMCAwMS0uMzEyLS44ODEgMi44MDggMi44MDggMCAwMS4wNC0xLjEzOCAzLjk5OCAzLjk5OCAwIDAxNC4yMi0zLjA3NyAyLjggMi44IDAgMDExLjA5My4zMTNjLjI5NC4xNTUuNTM4LjM0Ny43NDIuNTY4LjEwMi4xMS4xOS4yMy4yOC4zNWwuMjcuMzU5LjUzMi43MmE4OC4wNTkgODguMDU5IDAgMDEyLjA2IDIuOTM2IDczLjAzNiA3My4wMzYgMCAwMTEuOTI5IDMuMDNjLjE4Ny4zMTMuMzczLjYyOC41NTYuOTQ1IDIuNzI0LS4wNDcgNS40NDctLjA1NiA4LjE3LS4wMzguNzQ4LjAwNiAxLjQ5Ni4wMTUgMi4yNDQuMDI2LjE4LS4zMTMuMzY0LS42MjQuNTQ5LS45MzRhNzMuMjgxIDczLjI4MSAwIDAxMS45My0zLjAzIDg4LjczNyA4OC43MzcgMCAwMTIuMDU5LTIuOTM1bC41MzMtLjcyLjI2OC0uMzU5Yy4wOS0uMTIuMTc5LS4yNC4yODEtLjM1YTIuOCAyLjggMCAwMTEuODM0LS44ODF6TTMwLjEzIDM0LjYzMWE0IDQgMCAwMC0uNDE4IDEuNDIgOTEuMTU3IDkxLjE1NyAwIDAwLS40NDYgOS4xMjhjMCAyLjgyOC4xMjEgNS42NTYuMzY0IDguNDgzbC4xMSAxLjIxMmE0IDQgMCAwMDUuODU4IDMuMTQzYzIuODItMS40OTggNS41NS0zLjAzMyA4LjE5My00LjYwNmExNzcuNDEgMTc3LjQxIDAgMDA1Ljg5Ni0zLjY2NmwxLjQzNC0uOTQyYTQgNCAwIDAwLjA0Ny02LjYzMiAxMzcuNzAzIDEzNy43MDMgMCAwMC03LjM3Ny00LjcwOCAxNDYuODggMTQ2Ljg4IDAgMDAtNi44NzktMy44NDlsLTEuNC0uNzI1YTQgNCAwIDAwLTUuMzgyIDEuNzQyeiIgaWQ9InBpZC02NC1zdmdvLWQiPjwvcGF0aD48ZmlsdGVyIHg9Ii0xNS40JSIgeT0iLTE2LjMlIiB3aWR0aD0iMTMwLjklIiBoZWlnaHQ9IjEzMi41JSIgZmlsdGVyVW5pdHM9Im9iamVjdEJvdW5kaW5nQm94IiBpZD0icGlkLTY0LXN2Z28tYyI+PGZlT2Zmc2V0IGR5PSIyIiBpbj0iU291cmNlQWxwaGEiIHJlc3VsdD0ic2hhZG93T2Zmc2V0T3V0ZXIxIj48L2ZlT2Zmc2V0PjxmZUdhdXNzaWFuQmx1ciBzdGREZXZpYXRpb249IjEiIGluPSJzaGFkb3dPZmZzZXRPdXRlcjEiIHJlc3VsdD0ic2hhZG93Qmx1ck91dGVyMSI+PC9mZUdhdXNzaWFuQmx1cj48ZmVDb2xvck1hdHJpeCB2YWx1ZXM9IjAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAuMyAwIiBpbj0ic2hhZG93Qmx1ck91dGVyMSIgcmVzdWx0PSJzaGFkb3dNYXRyaXhPdXRlcjEiPjwvZmVDb2xvck1hdHJpeD48ZmVPZmZzZXQgaW49IlNvdXJjZUFscGhhIiByZXN1bHQ9InNoYWRvd09mZnNldE91dGVyMiI+PC9mZU9mZnNldD48ZmVHYXVzc2lhbkJsdXIgc3RkRGV2aWF0aW9uPSIzLjUiIGluPSJzaGFkb3dPZmZzZXRPdXRlcjIiIHJlc3VsdD0ic2hhZG93Qmx1ck91dGVyMiI+PC9mZUdhdXNzaWFuQmx1cj48ZmVDb2xvck1hdHJpeCB2YWx1ZXM9IjAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAuMiAwIiBpbj0ic2hhZG93Qmx1ck91dGVyMiIgcmVzdWx0PSJzaGFkb3dNYXRyaXhPdXRlcjIiPjwvZmVDb2xvck1hdHJpeD48ZmVNZXJnZT48ZmVNZXJnZU5vZGUgaW49InNoYWRvd01hdHJpeE91dGVyMSI+PC9mZU1lcmdlTm9kZT48ZmVNZXJnZU5vZGUgaW49InNoYWRvd01hdHJpeE91dGVyMiI+PC9mZU1lcmdlTm9kZT48L2ZlTWVyZ2U+PC9maWx0ZXI+PC9kZWZzPjxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCIgb3BhY2l0eT0iLjgiPjxtYXNrIGlkPSJwaWQtNjQtc3Znby1iIiBmaWxsPSIjZmZmIj48dXNlIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1hIj48L3VzZT48L21hc2s+PGcgbWFzaz0idXJsKCNwaWQtNjQtc3Znby1iKSI+PHVzZSBmaWxsPSIjMDAwIiBmaWx0ZXI9InVybCgjcGlkLTY0LXN2Z28tYykiIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1kIj48L3VzZT48dXNlIGZpbGw9IiNGRkYiIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1kIj48L3VzZT48L2c+PC9nPjwvc3ZnPg==">',
				switch: autoPlayVideo.value,
				onSwitch: function (item, $dom, event) {
					const nextState = !item.switch

					autoPlayVideo.value = nextState
					art.autoplay = nextState
					// 改变提示文本
					item.tooltip = nextState ? '开启' : '关闭'

					// 改变按钮状态
					return nextState
				},
			},
			{
				html: '自动播放下一个视频',
				tooltip: autoPlayNextVideo.value ? '开启' : '关闭',
				icon: '<img width="22" heigth="22" src="data:image/svg+xml;base64,Cjxzdmcgdmlld0JveD0iMCAwIDgwIDgwIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48ZGVmcz48cGF0aCBpZD0icGlkLTY0LXN2Z28tYSIgZD0iTTAgMGg4MHY4MEgweiI+PC9wYXRoPjxwYXRoIGQ9Ik01Mi41NDYgOC4wMTRhMy45OTggMy45OTggMCAwMTQuMjIyIDMuMDc3Yy4xMDQuNDQ2LjA5My44MDguMDM5IDEuMTM4YTIuNzQgMi43NCAwIDAxLS4zMTIuODgxYy0uMDczLjEzMi0uMTYuMjU0LS4yNDYuMzc2bC0uMjU3LjM2Ni0uNTIxLjczYy0uNy45NjktMS40MTUgMS45MjYtMi4xNTQgMi44NjZsLS4wMTUuMDJhMjQwLjk0NSAyNDAuOTQ1IDAgMDE1Ljk4Ni4zNDFsMS42NDMuMTIzLjgyMi4wNjYuNDEuMDM0LjIwNi4wMTguMTAzLjAwOC4xMTUuMDEyYzEuMjY2LjExNiAyLjUxNi40NSAzLjY3Ny45NzVhMTEuNjYzIDExLjY2MyAwIDAxMy4xNjYgMi4xMTRjLjkzMS44NyAxLjcxOSAxLjg5NSAyLjMyMSAzLjAyMmExMS41OTUgMTEuNTk1IDAgMDExLjIyNCAzLjYxM2MuMDMuMTU3LjA0Ni4zMTYuMDY4LjQ3NGwuMDE1LjExOS4wMTMuMTEyLjAyMi4yMDYuMDg1LjgyMi4xNTkgMS42NDZjLjEgMS4wOTguMTkgMi4xOTguMjcgMy4yOTguMzE1IDQuNC40NjMgOC44MjkuMzYgMTMuMjU1YTE2Ni40ODkgMTY2LjQ4OSAwIDAxLS44NDMgMTMuMjEzYy0uMDEyLjEyNy0uMDM0LjI5Ny0uMDUzLjQ1NGE3LjU4OSA3LjU4OSAwIDAxLS4wNzIuNDc1bC0uMDQuMjM3LS4wNS4yMzZhMTEuNzYyIDExLjc2MiAwIDAxLS43NCAyLjI4NyAxMS43NTUgMTEuNzU1IDAgMDEtNS4xMTggNS41NyAxMS43MDUgMTEuNzA1IDAgMDEtMy42MjMgMS4yNjNjLS4xNTguMDI0LS4zMTYuMDUyLS40NzUuMDcybC0uNDc3LjA1My0uODIxLjA3MS0xLjY0NC4xMzRjLTEuMDk2LjA4Ni0yLjE5Mi4xNi0zLjI4OC4yM2EyNjAuMDggMjYwLjA4IDAgMDEtNi41NzguMzI1Yy04Ljc3Mi4zMjQtMTcuNTQ2LjIyLTI2LjMxMy0uMzAyYTI0Mi40NTggMjQyLjQ1OCAwIDAxLTMuMjg3LS4yMmwtMS42NDMtLjEyOS0uODIyLS4wNjktLjQxLS4wMzUtLjIwNi0uMDE4Yy0uMDY4LS4wMDYtLjEzMy0uMDEtLjIxOC0uMDJhMTEuNTY2IDExLjU2NiAwIDAxLTMuNy0uOTkyIDExLjczMiAxMS43MzIgMCAwMS01LjQ5Ny01LjE3OCAxMS43MyAxMS43MyAwIDAxLTEuMjE1LTMuNjI3Yy0uMDI0LS4xNTgtLjA1MS0uMzE2LS4wNjctLjQ3NWwtLjAyNi0uMjM4LS4wMTMtLjExOS0uMDEtLjEwMy0uMDctLjgyMy0uMTMyLTEuNjQ4YTE5MC42MzcgMTkwLjYzNyAwIDAxLS4yMi0zLjI5OGMtLjI1Ni00LjM5OS0uMzU4LTguODE3LS4yNTgtMTMuMjMzLjA5OS00LjQxMi4zNzItOC44MTEuNzg4LTEzLjE5N2ExMS42NSAxMS42NSAwIDAxMy4wMzktNi44MzUgMTEuNTg1IDExLjU4NSAwIDAxNi41NzItMy41NjNjLjE1Ny0uMDIzLjMxMi0uMDUxLjQ3LS4wN2wuNDctLjA1LjgyLS4wNyAxLjY0My0uMTNhMjI4LjQ5MyAyMjguNDkzIDAgMDE2LjY0Ny0uNDA1bC0uMDQxLS4wNWE4OC4xNDUgODguMTQ1IDAgMDEtMi4xNTQtMi44NjdsLS41Mi0uNzMtLjI1OC0uMzY2Yy0uMDg2LS4xMjItLjE3My0uMjQ0LS4yNDYtLjM3NmEyLjc0IDIuNzQgMCAwMS0uMzEyLS44ODEgMi44MDggMi44MDggMCAwMS4wNC0xLjEzOCAzLjk5OCAzLjk5OCAwIDAxNC4yMi0zLjA3NyAyLjggMi44IDAgMDExLjA5My4zMTNjLjI5NC4xNTUuNTM4LjM0Ny43NDIuNTY4LjEwMi4xMS4xOS4yMy4yOC4zNWwuMjcuMzU5LjUzMi43MmE4OC4wNTkgODguMDU5IDAgMDEyLjA2IDIuOTM2IDczLjAzNiA3My4wMzYgMCAwMTEuOTI5IDMuMDNjLjE4Ny4zMTMuMzczLjYyOC41NTYuOTQ1IDIuNzI0LS4wNDcgNS40NDctLjA1NiA4LjE3LS4wMzguNzQ4LjAwNiAxLjQ5Ni4wMTUgMi4yNDQuMDI2LjE4LS4zMTMuMzY0LS42MjQuNTQ5LS45MzRhNzMuMjgxIDczLjI4MSAwIDAxMS45My0zLjAzIDg4LjczNyA4OC43MzcgMCAwMTIuMDU5LTIuOTM1bC41MzMtLjcyLjI2OC0uMzU5Yy4wOS0uMTIuMTc5LS4yNC4yODEtLjM1YTIuOCAyLjggMCAwMTEuODM0LS44ODF6TTMwLjEzIDM0LjYzMWE0IDQgMCAwMC0uNDE4IDEuNDIgOTEuMTU3IDkxLjE1NyAwIDAwLS40NDYgOS4xMjhjMCAyLjgyOC4xMjEgNS42NTYuMzY0IDguNDgzbC4xMSAxLjIxMmE0IDQgMCAwMDUuODU4IDMuMTQzYzIuODItMS40OTggNS41NS0zLjAzMyA4LjE5My00LjYwNmExNzcuNDEgMTc3LjQxIDAgMDA1Ljg5Ni0zLjY2NmwxLjQzNC0uOTQyYTQgNCAwIDAwLjA0Ny02LjYzMiAxMzcuNzAzIDEzNy43MDMgMCAwMC03LjM3Ny00LjcwOCAxNDYuODggMTQ2Ljg4IDAgMDAtNi44NzktMy44NDlsLTEuNC0uNzI1YTQgNCAwIDAwLTUuMzgyIDEuNzQyeiIgaWQ9InBpZC02NC1zdmdvLWQiPjwvcGF0aD48ZmlsdGVyIHg9Ii0xNS40JSIgeT0iLTE2LjMlIiB3aWR0aD0iMTMwLjklIiBoZWlnaHQ9IjEzMi41JSIgZmlsdGVyVW5pdHM9Im9iamVjdEJvdW5kaW5nQm94IiBpZD0icGlkLTY0LXN2Z28tYyI+PGZlT2Zmc2V0IGR5PSIyIiBpbj0iU291cmNlQWxwaGEiIHJlc3VsdD0ic2hhZG93T2Zmc2V0T3V0ZXIxIj48L2ZlT2Zmc2V0PjxmZUdhdXNzaWFuQmx1ciBzdGREZXZpYXRpb249IjEiIGluPSJzaGFkb3dPZmZzZXRPdXRlcjEiIHJlc3VsdD0ic2hhZG93Qmx1ck91dGVyMSI+PC9mZUdhdXNzaWFuQmx1cj48ZmVDb2xvck1hdHJpeCB2YWx1ZXM9IjAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAuMyAwIiBpbj0ic2hhZG93Qmx1ck91dGVyMSIgcmVzdWx0PSJzaGFkb3dNYXRyaXhPdXRlcjEiPjwvZmVDb2xvck1hdHJpeD48ZmVPZmZzZXQgaW49IlNvdXJjZUFscGhhIiByZXN1bHQ9InNoYWRvd09mZnNldE91dGVyMiI+PC9mZU9mZnNldD48ZmVHYXVzc2lhbkJsdXIgc3RkRGV2aWF0aW9uPSIzLjUiIGluPSJzaGFkb3dPZmZzZXRPdXRlcjIiIHJlc3VsdD0ic2hhZG93Qmx1ck91dGVyMiI+PC9mZUdhdXNzaWFuQmx1cj48ZmVDb2xvck1hdHJpeCB2YWx1ZXM9IjAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAuMiAwIiBpbj0ic2hhZG93Qmx1ck91dGVyMiIgcmVzdWx0PSJzaGFkb3dNYXRyaXhPdXRlcjIiPjwvZmVDb2xvck1hdHJpeD48ZmVNZXJnZT48ZmVNZXJnZU5vZGUgaW49InNoYWRvd01hdHJpeE91dGVyMSI+PC9mZU1lcmdlTm9kZT48ZmVNZXJnZU5vZGUgaW49InNoYWRvd01hdHJpeE91dGVyMiI+PC9mZU1lcmdlTm9kZT48L2ZlTWVyZ2U+PC9maWx0ZXI+PC9kZWZzPjxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCIgb3BhY2l0eT0iLjgiPjxtYXNrIGlkPSJwaWQtNjQtc3Znby1iIiBmaWxsPSIjZmZmIj48dXNlIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1hIj48L3VzZT48L21hc2s+PGcgbWFzaz0idXJsKCNwaWQtNjQtc3Znby1iKSI+PHVzZSBmaWxsPSIjMDAwIiBmaWx0ZXI9InVybCgjcGlkLTY0LXN2Z28tYykiIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1kIj48L3VzZT48dXNlIGZpbGw9IiNGRkYiIHhsaW5rOmhyZWY9IiNwaWQtNjQtc3Znby1kIj48L3VzZT48L2c+PC9nPjwvc3ZnPg==">',
				switch: autoPlayNextVideo.value,
				onSwitch: function (item, $dom, event) {
					const nextState = !item.switch

					autoPlayNextVideo.value = nextState

					// 改变提示文本
					item.tooltip = nextState ? '开启' : '关闭'

					// 改变按钮状态
					return nextState
				},
			},
		],
		controls: [
			{
				position: 'right',
				html: hiddenTools.value ? hiddenIcon : notHiddenIcon,
				click: function (_, event) {
					hiddenTools.value = !hiddenTools.value
					event.target.parentNode.parentNode.innerHTML = hiddenTools.value
						? hiddenIcon
						: notHiddenIcon
				},
			},
			{
				name: 'video-list',
				position: 'right',
				html: videoListIcon,
				selector: getVideoList(name),
				onSelect: function (item, $dom) {
					initArtPlayer(item.html, item.url)
					return videoListIcon
				},
			},
		],
	}

	let subtitles = getSubtitles(name)
	if (subtitles.length > 0) {
		// 如果字幕数量大于 1, 则支持切换字幕
		if (subtitles.length > 1) {
			options.controls.push({
				name: 'video-subtitle',
				position: 'right',
				html: subtitleIcon,
				selector: subtitles,
				onSelect: (item) => {
					if (item.html === '关闭字幕') {
						art.subtitle.show = false
					} else {
						art.subtitle.show = true
						art.subtitle.url = item.url
					}
					return subtitleIcon
				},
			})
		}
	}

	art = new Artplayer(options)

	// art.on('error', (count) => {
	// 	if (count === 5) {
	// 		ElMessage.warning({
	// 			title: '提示',
	// 			message: '加载视频失败, 可能浏览器不支持此视频格式的解码，可尝试使用本地播放器打开',
	// 			duration: 5000,
	// 			showClose: true,
	// 			grouping: true
	// 		});
	// 	}
	// });

	art.on('destory', () => {})

	if (common.isMobile.value === false) {
		art.on('ready', () => {
			let prevAndNextVideo = getPrevAndNextVideo(art.option.title)
			if (prevAndNextVideo.prev) {
				art.controls.add({
					name: 'prev-video',
					position: 'left',
					index: 5,
					tooltip: '播放上一个',
					html: prevVideoIcon,
					click: () => {
						playPrevVideo()
					},
				})
			}
			if (prevAndNextVideo.next) {
				art.controls.add({
					name: 'next-video',
					position: 'left',
					index: 15,
					tooltip: '播放下一个',
					html: nextVideoIcon,
					click: () => {
						playNextVideo()
					},
				})
			}
		})
	}
	art.on('video:ended', () => {
		if (autoPlayNextVideo.value) {
			playNextVideo()
		}
	})

	// 默认第一个为字幕
	if (subtitles.length > 0) {
		art.subtitle.url = subtitles[0].url
	}
}

const playNextVideo = () => {
	const nextVideo = getPrevAndNextVideo(art.option.title).next
	if (nextVideo) {
		initArtPlayer(nextVideo.name, nextVideo.url)
	}
}

const playPrevVideo = () => {
	const prevVideo = getPrevAndNextVideo(art.option.title).prev
	if (prevVideo) {
		initArtPlayer(prevVideo.name, prevVideo.url)
	}
}

onMounted(() => {
	initArtPlayer(
		fileDataStore.currentClickRow.name,
		fileDataStore.currentClickRow.url
	)
})
</script>

<style scoped lang="scss">
.artplayer-app {
	@apply h-[40vh] md:h-[60vh] lg:h-[70vh];

	:deep(.art-controls svg) {
		height: initial;
		width: initial;
	}

	:deep(.art-controls .art-control) {
		min-height: 14px;
		min-width: 14px;
		.art-icon {
			width: 28px;
		}
	}
}

.juyo-video-switch-tools {
	@apply px-5 py-2 bg-gray-50 flex justify-between;
}
.juyo-video-tools {
	@apply bg-gray-50 p-3
        grid grid-cols-3 gap-0
        sm:space-x-10 sm:flex sm:justify-center sm:flex-wrap;

	.juyo-video-tools-item {
		@apply bg-white shadow hover:shadow-2xl m-2 px-2 py-1 rounded-md flex-shrink-0 cursor-pointer text-center;

		img {
			@apply w-8 h-8 inline;
		}
	}
}

.juyo-video-tools-tips {
	@apply bg-gray-50 text-center pb-2 text-gray-500 text-sm;
}
</style>
