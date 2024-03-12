<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="scss">
html,
body {
  background: linear-gradient(45deg, #34495E, #41B883);
  background-repeat: no-repeat;
  background-size:100% 100%; 
  background-attachment:fixed;
  height: 100%;
  margin: 0;
  padding: 0;
}
#app {
  height: 100%;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.fa {
  margin-right: 5px;
}
.warning {
  position: absolute;
  right: 2rem;
  color: #F56C6C;
  animation: wanringmove 0.2s;
}
@keyframes wanringmove {
  0% {
    right: 1rem;
  }
  25% {
    right: 3rem;
  }
  50% {
    right: 1rem;
  }
  75% {
    right: 3rem;
  }
  100% {
    right: 0px;
  }
}
</style>
