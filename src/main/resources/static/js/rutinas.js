/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function addCard(formulario){ 
var valor = formulario.elements[0].value;
var url = '/carrito/agregar';
url = url + '/' + valor;
$("#resultsBlock").load(url);}
 