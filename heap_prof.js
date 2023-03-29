class MinHeap {

  // O heap deve ser inicializado com um vetor contendo
  // N elementos. A quantidade de elementos passada será
  // considerada o tamanho do heap
  constructor(vet) {
    console.log(vet.map(i => i2s(i)));
    this.heap = vet.map(i => { return { segment: 0, value: i } });
    for (let i = this.heap.length - 1; i >= 0; i--)
      this.sink(i);
  }

  left(i) {
    return i * 2 + 1;
  }

  right(i) {
    return i * 2 + 2;
  }

  size() {
    return this.heap.length;
  }

  ord(e1, e2) {  // em ordem
    return (e1.segment < e2.segment || (e1.segment == e2.segment && e1.value < e2.value));
  }

  sink(i) {
    if (i >= this.heap.length)
      return;

    while (i < this.heap.length) {
      if (this.left(i) >= this.heap.length) // sem filhos
        return;
      if (this.left(i) == this.heap.length - 1) { // só o filho esquerdo
        if (!this.ord(this.heap[i], this.heap[this.left(i)]))
          this.change(i, this.left(i));
        return;
      }
      if (this.ord(this.heap[this.left(i)], this.heap[this.right(i)])) {
        if (!this.ord(this.heap[i], this.heap[this.left(i)])) {
          this.change(i, this.left(i));
          i = this.left(i);
          this.sink(i);
        }
        else break;
      }
      else {
        if (!this.ord(this.heap[i], this.heap[this.right(i)])) {
          this.change(i, this.right(i));
          i = this.right(i);
          this.sink(i);
        }
        else break;
      }
    }
  }

  change(i1, i2) {
    let aux = this.heap[i1];
    this.heap[i1] = this.heap[i2];
    this.heap[i2] = aux;
  }

  insert(val) {
    let min = this.heap[0];
    if (val < min.value)
      this.heap[0] = { segment: min.segment + 1, value: val };
    else
      this.heap[0] = { segment: min.segment, value: val };
    this.sink(0);
    console.log(`\nRetira ${String.fromCharCode(min.value)} e insere ${String.fromCharCode(val)}`);
    // console.log(`\nRetira ${min.value} e insere ${val}`); 
    // this.print();
    return min.value;
  }

  remove() {
    let min = this.heap[0];
    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap = this.heap.slice(0, this.heap.length - 1);
    this.sink(0);
    console.log(`\nRetira ${String.fromCharCode(min.value)}`);
    // console.log(`\nRetira ${min.value}`); 
    // this.print();
    return min.value;
  }

  print() {  // somente até heap de tamanho 7
    console.log(`
      ${this.num(this.heap[0]?.value)}
      ${this.num(this.heap[1]?.value)}      ${this.num(this.heap[2]?.value)}
      ${this.num(this.heap[3]?.value)}  ${this.num(this.heap[4]?.value)}  ${this.num(this.heap[5]?.value)}  ${this.num(this.heap[6]?.value)}`);
  }

  num(a) {
    if (a < 10) return "0" + a;
    else return "" + a;
  }
}