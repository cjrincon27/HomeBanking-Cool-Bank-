let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get('id');
const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            accounts: [],
            accountsId: [],
            transaction: [],  
        }
    },
    created(){ 
        axios.get ('/api/clients/current')
            .then((response) => {
                this.clients = response.data;
                this.accounts = response.data.accounts;
                this.accountsId = this.accounts.find(account => account.id == urlName);
                this.transaction = this.accountsId.transactions;
            }  )    
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
    },
}).mount('#app')