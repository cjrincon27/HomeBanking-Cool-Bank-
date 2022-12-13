const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                accounts: [],
                accountsId: [],
                // transferType: [],
                // otherAccount: "",
                amount: "",
                description: "",
                accountOrigin: "",
                accountDestiny: "",
                amount: 0,
                description: "",
                cuentaOrigen: true,
                cuentaDestino: false,
            }
        },

        created() {
            axios
                .get("/api/clients/current")
                .then((response) => {
                    this.clients = response.data;
                    this.accounts = this.clients.accounts;
                    this.accountsId = this.accounts.find((account) => account.id == urlName);
                    
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        methods: {

            newTransfer() {
                axios
                    .post(
                        "/api/transactions",
                        `amount=${this.amount}&description=${this.description}&accountOrigin=${this.accountOrigin}&accountDestiny=${this.accountDestiny}`)
                    .then((response) => {
                        
                        Swal.fire({
                            title: "SUCCESFULL TRANSACTION!!!",
                            html: 'I will close in <b></b> miliseconds.',
                            footer: '<button type="button" class="btn btn-primary" data-bs-dismiss="modal"><a href="./accounts.html">BACK TO DASHBOARD</a></button>',
                            timer: 1000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading()
                                const b = Swal.getHtmlContainer().querySelector('b')
                                timerInterval = setInterval(() => {
                                    b.textContent = Swal.getTimerLeft()
                                }, 10)
                            },
                            willClose: () => {
                                clearInterval(timerInterval)
                            }

                        }).then((result) => {
            
                            if (result.dismiss === Swal.DismissReason.timer) {
                                console.log('I was closed by the timer')
                            }
                        })
                        setTimeout(() => { location.href = "/web/transfers.html" }, 3000);

                    })
                    .catch((error) => {
                        console.log(error);
                        Swal.fire("WE CAN'T DO THIS TRANSACTION" + " (" + error.response.data + ")")
                    });
            },

            cuentaOrigens() {
                this.cuentaOrigen = true;
                this.cuentaDestino = false;
            },
            cuentaDestinos() {
                this.cuentaDestino = true;
                this.cuentaOrigen = false;
            },

            logOut() {
                axios.post('/api/logout')
                    .then(response => console.log('signed out!!!'))
            },
        },
    }).mount('#app')