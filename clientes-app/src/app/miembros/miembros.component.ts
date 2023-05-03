import { Component, OnInit } from '@angular/core';
import { Miembro } from './miembro';
import { MiembroService } from './miembro.service';
import { ModalmService } from './detallem/modalm.service';
import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../usuarios/auth.service';

@Component({
  selector: 'app-miembros',
  templateUrl: './miembros.component.html'
})
export class MiembrosComponent implements OnInit {

  miembros: Miembro[] = [];
  paginadorm: any;
  miembroSeleccionado: Miembro;

  constructor(private miembroService: MiembroService,
    public modalService: ModalmService,
    public authService: AuthService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {

    this.activatedRoute.paramMap.subscribe(params => {
      let page: number = +params.get('page');
      if (!page) {
        page = 0;
      }

      this.miembroService.getMiembros(page)
        .pipe(
          tap(response => {
            console.log('MiembrosComponent: tap 3');
            (response.content as Miembro[]).forEach(miembro => console.log(miembro.nombre));
          })
        ).subscribe(response => {
          this.miembros = response.content as Miembro[];
          this.paginadorm = response;
        });
    });

    this.modalService.notificarUpload.subscribe(miembro => {
      this.miembros = this.miembros.map(miembroOriginal => {
        if(miembro.id == miembroOriginal.id){
          miembroOriginal.foto = miembro.foto;
        }
        return miembroOriginal;
      })
    });
  }

  delete(miembro: Miembro):void{
    swal({
      title: '¿Está seguro?',
      text: `¿Seguro que desea eliminar al miembro ${miembro.nombre} ${miembro.apellido}?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.miembroService.delete(miembro.id).subscribe(
          () => {
            this.miembros = this.miembros.filter(cli => cli !== miembro)
            swal(
              'Miembro eliminado!',
              `Miembro ${miembro.nombre} eliminado con éxito.`,
              'success'
            )
          }
        )
      }
    })
  }

  abrirModal(miembro: Miembro){
    this.miembroSeleccionado = miembro;
    this.modalService.abrirModal();
  }

}
