const app = Vue.createApp({
data() {
    return {
        clients: [],
        clientsId: [],
        firstName: "",
        lastName: "",
        email: "",
        id: "",
        };
    },

created() {
    this.loadData();
},

methods: {
    loadData() {
        axios.get('/clients').then((response) => {
        this.clients = response.data._embedded.clients;
        this.clientsId = this.clients.map((id) => id._links.self.href);
        })
    },

addClient() {
        if (this.firstName != "" && this.lastName != "" && this.email != "") {
            axios.post("/clients", {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            })
            .then(() => this.loadData())
            .then(() => {
            this.firstName = "";
            this.lastName = "";
            this.email = "";
            });
        }
    },

    deletClient(cliente) {
        axios.delete(cliente._links.client.href).then(() => this.loadData());
    },
    viewClient(client) {
        this.firstName = client.firstName;
        this.lastName = client.lastName;
        this.email = client.email;
        this.id = client._links.self.href;
    },
    editclient() {
        if (this.firstName != "" && this.lastName != "" && this.email != "") {
        let replaceClient = {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
        };
        this.Replace(replaceClient);
        }
    },

    Replace(client) {
        axios.put(this.id,client).then(() => this.loadData());
    },
},
}).mount("#app");
