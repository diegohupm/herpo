import { Component, OnInit, Input } from '@angular/core';
import { Miembro } from '../miembro';
import { MiembroService } from '../miembro.service';
import { ModalmService } from './modalm.service';
// import { ActivatedRoute, Route } from '@angular/router';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { AuthService } from '../../usuarios/auth.service';

import { TareaService } from '../../tareas/services/tarea.service';
import { Tarea } from '../../tareas/models/tarea';
import { Subtarea } from '../../tareas/models/subtarea';

@Component({
  selector: 'detallem-miembro',
  templateUrl: './detallem.component.html',
  styleUrls: ['./detallem.component.css']
})
export class DetallemComponent implements OnInit {

  @Input() miembro: Miembro;
  titulo: string = "Detalle del miembro";
  private fotoSeleccionada: File;
  progreso: number =0;

  constructor( private miembroService: MiembroService,
  public authService: AuthService,
  private tareaService: TareaService,
  private router: Router,
  public modalService: ModalmService) { }

  ngOnInit() {

  }

seleccionarFoto(event:any){
  this.fotoSeleccionada = event.target.files[0];
  this.progreso = 0;
  console.log(this.fotoSeleccionada);
  if(this.fotoSeleccionada.type.indexOf('image') < 0){
    swal('Error selección de imagen: ', 'El archivo debe ser del tipo imagen', 'error');
    this.fotoSeleccionada=null;
  }
}

subirFoto(){

  if(!this.fotoSeleccionada){
    swal('Error subida: ', 'Debe seleccionar una foto', 'error');
  }else{
    this.miembroService.subirFoto(this.fotoSeleccionada, this.miembro.id)
    .subscribe(event => {
      if(event.type === HttpEventType.UploadProgress){
        this.progreso = Math.round((event.loaded/event.total)*100);
      } else if(event.type === HttpEventType.Response){
        let response: any = event.body;
        this.miembro = response.miembro as Miembro;
        this.modalService.notificarUpload.emit(this.miembro);
        swal('La foto se ha subido completamente.', response.mensaje, 'success');
      }
    });
  }
}

cerrarModal(){
  this.modalService.cerrarModal();
  this.fotoSeleccionada=null;
  this.progreso = 0;
}

delete(tarea: Tarea): void {
    swal({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar la tarea ${tarea.descripcion}?`,
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

        this.tareaService.delete(tarea.id).subscribe(
          () => {
           this.miembro.tareas = this.miembro.tareas.filter(t => t !== tarea);
            swal(
              'Tarea Eliminada!',
              `Tarea ${tarea.descripcion} eliminada con éxito.`,
              'success'
            )
            this.router.navigate(['/miembros'])
          }
        )

      }
    });
  }

  deleteSubTarea(subtarea: Subtarea): void {
      swal({
        title: 'Está seguro?',
        text: `¿Seguro que desea eliminar la tarea ${subtarea.descripcion}?`,
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

          this.tareaService.deleteSubtarea(subtarea.id).subscribe(
            () => {
             this.miembro.subtareas = this.miembro.subtareas.filter(t => t !== subtarea);
              swal(
                'Tarea Eliminada!',
                `Tarea ${subtarea.descripcion} eliminada con éxito.`,
                'success'
              )
              this.router.navigate(['/miembros'])
            }
          )

        }
      });
    }

}
