const host = 'http://localhost:8090';

let model = new Model(host);
let view = new View(model);

view.init();
model.init();

