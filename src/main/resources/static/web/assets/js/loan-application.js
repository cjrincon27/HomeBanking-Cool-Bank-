let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get('id');

const app = Vue.
  createApp({
    data() {
      return {
        clients: [],
        accounts: [],
        payments: [],
        loans: [],
        payment: 0,
        loanType: "",
        loanPays: 0,
        amount: 0,
        accountDestiny: "",
        loanId: 0,
        // loanId: "",
        totalPayments: "",
      }
    },

    created() {
      axios
        .get("/api/clients/current")
        .then((response) => {
          this.clients = response.data;
          this.accounts = this.clients.accounts;
        })
        .catch(function (error) {
          console.log(error);
        });
      axios
      .get("/api/loans")
      .then((response) => {
      this.loans = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
      this.paymentsCalc();
      this.amountCalc();
    },

    methods: {

      loanPetition() {
        axios
          .post("/api/loans", {
            id: `${this.loanId}`,
            amount: `${this.amount}`,
            payments: `${this.loanPays}`,
            numberDestinAccount: `${this.accountDestiny}`,
          })
          .then((response) => {
            Swal.fire({
              title: "succes full!!!",
              html: "I will close in <b></b> miliseconds.",
              footer:
                '<button type="button" class="btn btn-primary" data-bs-dismiss="modal"><a href="./accounts.html">BACK TO DASHBOARD</a></button>',
              timer: 3000,
              timerProgressBar: true,
              didOpen: () => {
                Swal.showLoading();
                const b = Swal.getHtmlContainer().querySelector("b");
                timerInterval = setInterval(() => {
                  b.textContent = Swal.getTimerLeft();
                }, 10);
              },
              willClose: () => {
                clearInterval(timerInterval);
              },
            }).then((result) => {
              if (result.dismiss === Swal.DismissReason.timer) {
              
              }
            });
            setTimeout(() => {
              location.href = "/web/account.html";
            }, 3000);
  
            console.log("CREATED");
          })
          .catch((error) => {
            Swal.fire(
              " not is possible  generate a loan" + " (" + error.response.data + ")"
            );
            // console.log(error);
          });
      },
     
 
    paymentsCalc(){
      if(this.loanId != 0 && this.loanPays != "" ){
      if(this.loanId ==6){
        this.totalPayments= (this.amount / this.loanPays) *1.2
      }
      if(this.loanId ==7){
        this.totalPayments= (this.amount / this.loanPays) *1.10
      }
      if(this.loanId ==8){
        this.totalPayments= (this.amount / this.loanPays) *1.5
      }
      return this.totalPayments
    }
     
    },
    amountCalc(){
      if(this.loanId != 0 ){
      if(this.loanId ==6){
        this.totalPayments= this.amount *1.2
      }
      if(this.loanId ==7){
        this.totalPayments= this.amount *1.10
      }
      if(this.loanId ==8){
        this.totalPayments= this.amount *1.5
      }
      return this.totalPayments
    }
     
    },

    modificarSaldo(saldo){
      return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
  },
      newDate(creationDate){
      return new Date(creationDate).toLocaleDateString('es-AR', {month: '2-digit', year: '2-digit'});
    },
      logOut() {
        axios.post('/api/logout')
          .then(response => console.log('signed out!!!'))
      },
    },
  }).mount('#app')