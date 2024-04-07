const designs = [
    { id: 1, name: 'XXXX', price: 500000, image: '/src/assets/img/...' },
    { id: 2, name: 'XXXX', price: 1000000, image: '/src/assets/img/...' },
    // ...otros diseños
];

function listarDiseños() {
    const designList = document.getElementById('design-list'); 
    designs.forEach(design => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <img src="${'/src/assets/img/disenoP/01.jpg'}" alt="${design.name}">
            <h3>${design.name}</h3>
            <p>Precio: $${design.price.toFixed(2)}</p>
        `;
        designList.appendChild(listItem);
    });
}

window.addEventListener('load', listarDiseños);
