import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})

export class FooterComponent {
  public autor: any = {nombre:'Sergio', apellido: 'Herrero'};
  public autor2: any = {nombre:'Diego', apellido: 'Heras'};
}
