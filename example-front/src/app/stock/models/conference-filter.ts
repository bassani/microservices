export class ConferenceFilter {
    dtInicialPedido: Date;
    dtFinalPedido: Date;
    canal: string[];
    cdProdutos: number[];
    cdFiliais: number[];
}


export class IConferenceResult {
    
        codigoFilial: number;
        nomeFantasia: string;
        codigoProduto: number;
        descricaoProduto: string;
        dataPedido: Date;        
        descricaoFluxo: string;
        descricaoCanal: string;
        numeroPedido: number;
        quantidadeItem: number;
        dataCriacaoReserva:  Date;       
        dataExpiracaoReserva:  Date;       
        estoqueContabil: number;
        estoqueDisponivel: number;
        estoqueEmTransito: number;
        estoqueReserva: number;
        estoqueTrava: number;
      
}