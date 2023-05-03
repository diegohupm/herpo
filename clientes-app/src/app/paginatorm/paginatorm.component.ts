import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'paginatorm-nav',
  templateUrl: './paginatorm.component.html'
})
export class PaginatormComponent implements OnInit, OnChanges {

  @Input() paginadorm: any;

  paginas: number[];
  desde: number;
  hasta: number;

  constructor() { }

  ngOnInit() {
    this.initPaginatorm();
  }

  ngOnChanges(changes: SimpleChanges) {
    let paginadorActualizado = changes['paginadorm'];
    if(paginadorActualizado.previousValue){
      this.initPaginatorm();
    }
  }

  private initPaginatorm(): void{
    this.desde = Math.min(Math.max(1, this.paginadorm.number-1), this.paginadorm.totalPages-4);
    this.hasta = Math.max(Math.min(this.paginadorm.totalPages, this.paginadorm.number+3), 5);
    if(this.paginadorm.totalPages>5){
      this.paginas= new Array(this.hasta - this.desde + 1).fill(0).map((_valor, indice) => indice + this.desde);
    } else{
      this.paginas = new Array(this.paginadorm.totalPages).fill(0).map((_valor, indice) => indice + 1);
    }
  }

}
