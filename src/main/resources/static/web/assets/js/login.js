const app = Vue.createApp({
  data() {
    return {
      email: "",
      password: "",
    };
  },
  created() {},
  methods: {
    login() {
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`, {
        })
        .then(response => location.href = "/web/account.html")
        .catch (response => Swal.fire({
          icon: 'error',
          text: 'Wrong email or password! Please try again.',
        })
        );
    },
  },
}).mount("#app");

