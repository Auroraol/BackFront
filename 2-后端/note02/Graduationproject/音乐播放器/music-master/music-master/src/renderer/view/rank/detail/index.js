import { mapActions, mapState } from 'vuex'
import eventBus from '../eventBus'

export default {
    name: 'rankDetail',
    data() {
        return {
            loading: false,
            info: {
                list: [],
            },
        }
    },
    computed: {
        ...mapState('playlist', ['playlist']),
        ...mapState('user', {
            userInfo: 'info',
        }),
        id() {
            return this.$route.params.id
        },
        vendor() {
            return this.$route.query.vendor
        },
    },
    methods: {
        ...mapActions('play', ['play']),
        doPlay(item) {
            this.play({
                info: item,
                playlist: this.info.list,
            })
        },
        rowClassName({ row, rowIndex }) {
            const rs = [this.s.row]
            if (row.cp) {
                rs.push(this.s.disabled)
            }
            return rs.join(' ')
        },
        async getDetail() {
            this.loading = true
            try {
                const { data, status } = await this.$musicApi[
                    this.vendor
                ].getTopList(this.id)
                if (status) {
                    data.list.forEach(item => {
                        item.vendor = this.vendor
                        item.songId = item.id
                    })
                    this.info = data
                } else {
                    this.$message.warning('无法获取详情')
                }
            } catch (e) {
                console.warn(e)
            }
            this.loading = false
        },
        // 添加到歌单
        addToPlaylist() {
            this.$ipc.send('add-to-playlist', {
                songs: this.info.list,
                playlist: this.playlist,
            })
        },
    },
    created() {
        this.getDetail()
    },
}
