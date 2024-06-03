<template>
    <div class="browserBottom">
        <div class="pariseInfo">
            <div class="parise">
                <div class="pariseNum">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-dianzan"></use>
                    </svg>
                </div>
                <span>{{ pariseNum }}</span>
            </div>
            <div class="parise">
                <div class="collectionNum pariseNum">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-shoucang1"></use>
                    </svg>
                </div>
                <span>{{ collectionNum }}</span>
            </div>
        </div>
        <div class="others">
            <p>喜欢的话给作者点个赞吧</p>
            <p>发布时间：{{ articleInfo.time }}</p>
            <p>最后修改：{{ articleInfo.lastUpdataTime }}</p>
            <div>
                <span>作品标签：</span>
                <span class="labelTag" v-for="tag in articleInfo.tag" :key="tag">{{ tag }}</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { watchEffect, ref } from 'vue'
import { useStore } from '@/store/count'
import useAxios from '@/hooks/axios/axios';

const pinia = useStore()

type Props = {
    articleInfo: any
}
const props = defineProps<Props>()

//点赞数组的长度
const pariseNum = ref(props.articleInfo.parise.length)
//收藏数组的长度
const collectionNum = ref(props.articleInfo.collections.length)

watchEffect(async () => {
    if (pinia.pariseOrCollection) {
        const { data: res } = await useAxios.get('/getidarticle', {
            params: {
                id: props.articleInfo.id
            }
        })
        //更新点赞的数据
        pariseNum.value = res.data.parise.length
        //更新收藏数据
        collectionNum.value = res.data.collections.length
    }

})

</script>

<style scoped lang="less">
.browserBottom {
    border-radius: 0.5rem;
    padding: 1.4rem;
    box-shadow: .1rem .1rem .5rem var(--gray-sahdow);
    margin-top: 1rem;
    background-color: var(--white-background-color);

    .pariseInfo {
        height: 5rem;
        display: flex;
        justify-content: space-around;

        .parise {
            width: 10rem;
            display: flex;
            justify-content: flex-start;
            align-items: center;

            span {
                font-size: 2rem;
                margin-left: 1rem;
            }
        }

        .pariseNum {
            width: 5rem;
            height: 5rem;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 3rem;
            border: .3rem solid var(--border-line);
        }
    }

    .others {
        font-size: 1.2rem;
        margin-top: 1.5rem;

        p {
            margin: .3rem 0;
        }
    }
}

@media screen and (max-width:800px) {
    .browserBottom {
        width: 100%;
    }
}
</style>