window.onload = function () {
    console.log('here');
    var itensCarrinho = localStorage.getItem('itensCarrinho');
    if (itensCarrinho != null) {
        itensCarrinho = JSON.parse(itensCarrinho);
        $('.quantItensCarrinho').text(Object.keys(itensCarrinho).length);
    }
}