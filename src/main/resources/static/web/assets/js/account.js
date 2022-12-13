
const app = Vue.createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
    }
  },
  created() {
    axios.get('/api/clients/current')
      .then((response) => {
        this.clients = response.data;
        this.accounts = response.data.accounts;
        this.loans = response.data.loans;
      })
      .catch(function (error) {
        console.log(error);
      }
      );
  },
  methods: {
    logOut() {
      axios.post('/api/logout')
      .then(response => console.log('signed out!!!'))
    },
    createAccount() {
      axios.post('/api/clients/current/account' )
      .then( window.location.reload())
      .catch(() =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "You can't create more than 3 account!",
      })
      )
    }
  }
}).mount('#app')






