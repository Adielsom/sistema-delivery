document.addEventListener('DOMContentLoaded', () => {
    const addToCartButtons = document.querySelectorAll('.add-to-cart-button');
    const cartCounter = document.getElementById('cartCounter');

    //função para atualizar o contador do carrinho
    async function updateCartCounter() {
        try {
            const response = await fetch('/carrinho/totalItens');
            if (response.ok) {
                const data = await response.json();
                if (cartCounter) { //verifica se o elemento existe
                    cartCounter.textContent = data.totalItens;
                    // Torna o contador visível se houver itens, caso contrário, oculta
                    if (data.totalItens > 0) {
                        cartCounter.style.display = 'block';
                    } else {
                        cartCounter.style.display = 'none';
                    }
                }
            } else {
                console.error('Erro ao obter total de itens do carrinho:', response.statusText);
            }
        } catch (error) {
            console.error('Erro na requisição para obter total de itens do carrinho:', error);
        }
    }

    //adiciona event listener para cada botão "Adicionar ao Carrinho"
    addToCartButtons.forEach(button => {
        button.addEventListener('click', async (event) => {
            const productId = event.target.dataset.productId; // Pega o ID do produto do atributo data-
            const quantidade = 1; // Por enquanto, sempre adiciona 1 unidade

            try {
                const response = await fetch('/carrinho/adicionar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `produtoId=${productId}&quantidade=${quantidade}`
                });

                if (response.ok) {
                    const data = await response.json();
                    if (data.success) {
                        // Atualiza o contador do carrinho na interface
                        if (cartCounter) {
                            cartCounter.textContent = data.totalItens;
                            cartCounter.style.display = 'block'; // Garante que o contador esteja visível
                        }
                        alert('Produto adicionado ao carrinho!'); // Feedback simples
                    } else {
                        alert('Erro ao adicionar produto ao carrinho.');
                    }
                } else {
                    alert('Erro na requisição para adicionar produto ao carrinho.');
                }
            } catch (error) {
                console.error('Erro:', error);
                alert('Ocorreu um erro ao adicionar o produto ao carrinho.');
            }
        });
    });

    // Atualiza o contador do carrinho ao carregar a página
    updateCartCounter();
});