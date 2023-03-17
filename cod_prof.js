// Intercalação balanceada com segmentos de tamanho variável e 
// seleção por substituição
function IntercalacaoBalanceada3(chaves, tamBloco, caminhos) {

    // Variáveis auxiliares
    let i;
    let log = `INTERCALAÇÃO BALANCEADA DE ${caminhos} CAMINHOS COM SELEÇÃO POR SUBSTITUIÇÃO`;
    
    // Registra os dados originais
    log += `<br/><br/>DADOS: <br/>${chaves.join(' ')}<br/>Quantidade: ${chaves.length}`;
    
    // Cria o primeiro conjunto de destinos
    let destinos = new Array();
    for (i = 0; i < caminhos; i++) destinos.push(new Array());
    
    // Distribui as chaves
    let destino = 0; //  destino
    let blocoInicial = [];
    for(i=0; i<tamBloco && i<chaves.length; i++) {
      blocoInicial.push(chaves[i]);
    }
    let heap = new MinHeap(blocoInicial);
    // heap.print();
    
    let k, kAnterior = -Infinity;
    
    //Distriobuicao
    while (i < chaves.length) {
      k = heap.insert(chaves[i]);
      if(k<kAnterior) 
        if (++destino == caminhos) destino = 0;
      destinos[destino].push(k);
      kAnterior = k;
      i++;
    }
    while(heap.size()>0) {
      k = heap.remove();
      if(k<kAnterior) 
        if (++destino == caminhos) destino = 0;
      destinos[destino].push(k);
      kAnterior = k;
    }
    
    // Registra a distribuição
    log += `<br/><br/>DISTRIBUIÇÃO:`;
    for (i = 0; i < caminhos; i++)
      log += `<br/>Arq.${i + 1}: ${destinos[i].join(' ')}`;
    
    // Intercalações
    let tamSegmento = tamBloco;
    let intercalacao = 0;
    let direcao = 0; // 1 (1,2,3 -> 4,5,6) e 0 (4,5,6 -> 1,2,3)
    while (destinos[1].length > 0) {
      intercalacao++;
    
      // Transforma os destinos anteriores em fontes para a nova intercalação
      let fontes = destinos;
    
      // Cria os novos destinos e seus ponteiros
      let ponteiros = new Array();
      let fimSegmento = new Array();
      destinos = new Array();
      for (i = 0; i < caminhos; i++) {
        ponteiros.push(0);
        if (fontes[i].length > 0) fimSegmento.push(false);
        else fimSegmento.push(true);
        destinos.push(new Array());
      }
    
      // Intercala as fontes
      destino = 0;
      while (true) {
        // Testa se ainda há elementos a intercalar
        let continua = false;
        for (i = 0; i < caminhos; i++)
          if (ponteiros[i] < fontes[i].length) continua = true;
        if (!continua) break;
    
        // Testa a necessidade de mudança de destino
        let muda = true;
        for (i = 0; i < caminhos; i++) if (!fimSegmento[i]) muda = false;
        if (muda) {
          destino++;
          if (destino == caminhos) destino = 0;
          for (i = 0; i < caminhos; i++)
            if (ponteiros[i] < fontes[i].length) fimSegmento[i] = false;
        }
    
        // Encontra o menor entre as fontes
        let valores = [];
        for (i = 0; i < caminhos; i++) {
          if (!fimSegmento[i]) valores.push(fontes[i][ponteiros[i]]);
          else valores.push(Infinity);
        }
        let menor = valores.indexOf(Math.min(...valores));
    
        // Escreve o menor valor na fonte
        destinos[destino].push(fontes[menor][ponteiros[menor]]);
        ponteiros[menor]++;
        if (
          ponteiros[menor] == fontes[menor].length ||
          fontes[menor][ponteiros[menor]] < fontes[menor][ponteiros[menor]-1]
        )
          fimSegmento[menor] = true;
      }
    
      // Registra a intercalação
      log += `<br/><br/>${intercalacao}ª INTERCALAÇÃO:`;
      direcao = direcao ? 0 : 1; // inverte a direcao da intercalacao
      for (i = 0; i < caminhos; i++)
        log += `<br/>Arq.${i + 1 + direcao * caminhos}: ${destinos[i].join(' ')}`;
    
      tamSegmento *= caminhos;
    }
    
    return log;
    }