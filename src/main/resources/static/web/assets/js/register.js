const app = Vue.createApp({
    data() {
      return {
        email: "",
        password: "",
        firstName: "",
        lastName: "",
      };
    },
    created() {},
    methods: {
      login() {
        axios
          .post("/api/login", `email=${this.email}&password=${this.password}`, {
          })
          .then(response => location.href = "/web/account.html")
        },
        registerForm() {
            axios
              .post(
                "/api/clients",
                `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
              )
              .then(response => this.login())
          },
    }, 
  }).mount("#app");