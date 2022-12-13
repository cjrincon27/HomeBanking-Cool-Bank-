let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get('id');
const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            cards: [],
            debit:[],
            credit:[],  
        }
    },
    created(){ 
        axios.get ('/api/clients/current')
            .then((response) => {
                this.clients = response.data;
                this.cards = this.clients.cards;
                this.debit = this.cards.filter(card => card.cardType == "DEBIT");
                this.credit = this.cards.filter(card => card.cardType == "CREDIT");
            }  )    
            .catch(function (error) {
            }
        ); 
    },
    methods: {
        newDate(creationDate){ 
            return  new Date(creationDate).toLocaleDateString('en-US', {month: '2-digit', year: '2-digit'});
        },
        logOut() {
            axios.post('/api/logout')
            .then(response => console.log('signed out!!!'))
          }
    },
}).mount('#app')