const designs = [
    { id: 1, name: 'Camiseta estampada', price: 25.99, image: '/src/assets' },
    { id: 2, name: 'Vestido floral', price: 49.99, image: '/src/assets/img/...' },
    // ...otros diseños
];

function listarDiseños() {
    const designList = document.getElementById('design-list'); 
    designs.forEach(design => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <img src="${'/src/assets/img/disenoP/01.jpg'}" alt="${'disenoP'}">
            <h3>${'disenoP'}</h3>
            <p>Precio: $${design.price.toFixed(2)}</p>
        `;
        designList.appendChild(listItem);
    });
}

window.addEventListener('load', listarDiseños);
