import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { MiembroService } from '../miembros/miembro.service';
// import { Tarea } from './models/tarea';
import { Subtarea } from './models/subtarea';
import { TareaService } from './services/tarea.service';

@Component({
  selector: 'app-subtareas',
  templateUrl: './subtareas.component.html'
})
export class SubtareasComponent implements OnInit {

  titulo: string = 'Crear una subtarea';
  subtarea: Subtarea = new Subtarea();

  constructor(private miembroService: MiembroService,
    private tareaService: TareaService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let miembroId = +params.get('miembroId');
      this.miembroService.getMiembro(miembroId).subscribe(miembro => this.subtarea.miembro = miembro);
    });

  }

  create(subtareaForm:any):void {
    console.log(this.subtarea);
  //  if(this.tarea.items.length == 0) {
    //  this.autocompleteControl.setErrors({'invalid':true});
    //}

  //  if(facturaForm.form.valid && this.tarea.items.length > 0) {
    if(subtareaForm.form.valid) {
      this.tareaService.createSubtarea(this.subtarea).subscribe(subtarea => {
      swal(this.titulo, `Subtarea ${subtarea.descripcion} creada con éxito`, 'success');
      this.router.navigate(['/miembros'])
    })
    }

  }

}
