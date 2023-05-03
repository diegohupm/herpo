import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Subtarea } from './models/subtarea';
import { TareaService } from './services/tarea.service';

@Component({
  selector: 'app-detalle-subtarea',
  templateUrl: './detalle-subtarea.component.html'
})
export class DetalleSubtareaComponent implements OnInit {

  subtarea: Subtarea;
  titulo: string = 'Subtarea';

  constructor(private tareasService: TareaService,
  private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('id');
      this.tareasService.getSubtarea(id).subscribe(subtarea => {
        this.subtarea = subtarea;
      })
    })
  }

  guardarSubtarea(subtarea: Subtarea): void {
    swal({
      title: 'Está seguro?',
      text: `¿Seguro que desea modificar la subtarea ${subtarea.descripcion}?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, actualizar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.subtarea = subtarea;
        this.tareasService.createSubtarea(subtarea).subscribe(
          () => {
            swal(
              'Subtarea actualizada!',
              `Subtarea ${subtarea.descripcion} actualizada con éxito.`,
              'success'
            )
          //  this.router.navigate(['/miembros'])
          }
        )

      }
    });
  }
}
