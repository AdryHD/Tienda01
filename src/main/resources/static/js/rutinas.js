/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


window.addCard = function(formulario) {
const id = formulario.querySelector('input[name="idProducto"]').value;
const url = '/carrito/agregar/' + encodeURIComponent(id);
$("#resultsBlock").load(url);
};