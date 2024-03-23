import productoController from "./functions";
//Logica de los botones y de mas elementos que no son directamente metodos

//Metodo que muestra los productos en la pagina apenas abra
document.addEventListener('DOMContentLoaded', function() {
  obtenerProductos();
});

// Obtener referencia a los elementos de género
const hombresBtn = document.querySelector('.dropdown-content a:nth-child(1)');
const mujeresBtn = document.querySelector('.dropdown-content a:nth-child(2)');
const niñosBtn = document.querySelector('.dropdown-content a:nth-child(3)');

// Agregar eventos de clic a los botones de género
hombresBtn.addEventListener('click', () => {
  productoController.filtrarPorGenero('HOMBRE');
});

mujeresBtn.addEventListener('click', () => {
  productoController.filtrarPorGenero('MUJER');
});

niñosBtn.addEventListener('click', () => {
  productoController.filtrarPorGenero('NINO');
});
