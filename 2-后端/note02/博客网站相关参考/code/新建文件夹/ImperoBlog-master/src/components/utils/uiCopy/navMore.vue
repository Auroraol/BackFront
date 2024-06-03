<template>
    <input type="checkbox" id="checkbox" @click.stop>
    <label for="checkbox" class="toggle">
        <div class="bars" id="bar1"></div>
        <div class="bars" id="bar2"></div>
        <div class="bars" id="bar3"></div>
    </label>
</template>

<script setup lang="ts">

</script>

<style scoped>
#checkbox {
    display: none;
}

.toggle {
    position: relative;
    width: 4rem;
    height: 4rem;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 10px;
    transition-duration: .5s;
}

.bars {
    width: 100%;
    height: .5rem;
    background-color: #fff;
    border-radius: 4px;
    margin-top: .1rem;
}

#bar2 {
    transition-duration: .8s;
}

#bar1,
#bar3 {
    width: 100%;
}

#checkbox:checked+.toggle .bars {
    position: absolute;
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar2 {
    transform: scaleX(0);
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar1 {
    width: 100%;
    transform: rotate(45deg);
    transition-duration: .5s;
}

#checkbox:checked+.toggle #bar3 {
    width: 100%;
    transform: rotate(-45deg);
    transition-duration: .5s;
}

#checkbox:checked+.toggle {
    transition-duration: .5s;
    transform: rotate(180deg);
}
</style>