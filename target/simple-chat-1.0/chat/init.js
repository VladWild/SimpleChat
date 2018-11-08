const url = 'http://localhost:8090';
const urn = '/chat';
const uri = url + urn;

const update = 2000;

let model = new Model(url, uri, update);
let view = new View(model);

view.init();
model.init();



