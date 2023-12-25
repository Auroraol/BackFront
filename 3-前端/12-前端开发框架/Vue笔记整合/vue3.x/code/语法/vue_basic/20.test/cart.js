new Vue({
  el: "#cart",
  data() {
    return {
      cartList: [
        {
          id: 1,
          gname: "高麟--Spring Boot.从入门到实战",
          gprice: 79.8,
          count: 5,
        },
        {
          id: 2,
          gname: "Java Webi开发从入门到实战",
          gprice: 69.8,
          count: 10,
        },
        {
          id: 3,
          gname: "门ava EE框架整合开发入门到实战",
          gprice: 69.8,
          count: 100,
        },
      ],
    };
  },
  computed: {
    totalPrice() {
      var total = 0;
      for (var i = 0; i < this.cartList.length; i++) {
        var item = this.cartList[i];
        total = total + item.gprice * item.count;
      }
      return total;
    },
  },
  methods: {
    reduce(index) {
      if (this.cartList[index].count == 1) return;
      this.cartList[index].count--;
    },
    add(index) {
      this.cartList[index].count++;
    },
    remove(index) {
      this.cartList.splice(index, 1);
    },
    removeAl1() {
      this.cartList.splice(0, this.cartList.length);
    },
  },
});
