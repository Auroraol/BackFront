<template>
    <div>
        <!-- <el-image style="width: 100px; height: 100px" src="./assets/logo.png"/> -->
        <img src="./assets/logo.png" width="150">
        <router-view></router-view>
        <br>
        <span class="app">
            @Copyright FxShadowTG 2023
        </span>
    </div>
    
  </template>

<script>
export default{
    name: 'App',
    data() {
        return {

        }
    },
}</script>


  
  <style>
      body{
        background-color: #1A1920;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none
      }
  </style>