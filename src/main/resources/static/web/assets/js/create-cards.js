
const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                cards: [],
                debit: [],
                name: "",
                credit: [],
                selectedColor: null,
                options: [
                    { text: "SILVER", value: "SILVER" },
                    { text: "TITANIUM", value: "TITANIUM" },
                    { text: "GOLD", value: "GOLD" },
                ],
                selectedType: null,
                options: [
                    { value: "DEBIT", text: "DEBIT" },
                    { text: "CREDIT", value: "CREDIT" },
                ],
            }
        },
        created() {
            axios
                .get("/api/clients/current")
                .then((response) => {
                    this.clients = response.data;
                    this.cards = this.clients.cards;
                    this.debit = this.cards.filter((card) => card.cardType == "DEBIT");
                    this.credit = this.cards.filter((card) => card.cardType == "CREDIT");
                    this.name = this.clients.firstName + " " + this.clients.lastName;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        methods: {
            newDate(creationDate) {
                return new Date(creationDate).toLocaleDateString('en-US', { month: '2-digit', year: '2-digit' });
            },
            createCards() {
                axios
                    .post(
                        "/api/clients/current/cards",
                        `cardType=${this.selectedType}&cardColor=${this.selectedColor}`
                    )
                    .then((response) => window.location.href=("./cards.html"))
                    .catch((response) => Swal.fire({
                        icon: "error",
                        text: "sorry you can't create more cards of this type !",
                    }));
            },




        },
    }).mount('#app')