import { Component, OnInit } from '@angular/core';
import { Client } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Mensaje } from './models/mensaje';
import { AuthService } from '../usuarios/auth.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  private client: Client;

  conectado: boolean = false;

  mensaje: Mensaje = new Mensaje();
  mensajes: Mensaje[] = [];

  escribiendo: string;
  clienteId: string;
  proyecto: string;

  constructor(public authService: AuthService) {
    this.mensaje.username = this.authService.usuario.username;
    this.clienteId = 'id-' + new Date().getTime() + '-' + Math.random().toString(36).substr(2);
    this.proyecto = this.authService.usuario.proyecto;
    this.mensaje.proyecto = this.authService.usuario.proyecto;
  }

  ngOnInit() {
    this.client = new Client();
    this.client.webSocketFactory = () => {
      return new SockJS("http://localhost:8090/chat-websocket");
    }

    this.client.onConnect = (frame) => {
      console.log('Conectados: ' + this.client.connected + ' : ' + frame);
      this.conectado = true;

      console.log('Se ha conectado al chat del proyecto: ' + this.proyecto);
      this.client.subscribe('/chat/'+this.proyecto+'/mensaje', e => {
        let mensaje: Mensaje = JSON.parse(e.body) as Mensaje;
        mensaje.fecha = new Date(mensaje.fecha);

        if (!this.mensaje.color && mensaje.tipo == 'NUEVO_USUARIO' &&
          this.mensaje.username == mensaje.username) {
          this.mensaje.color = mensaje.color;
        }

        this.mensajes.push(mensaje);
        console.log(mensaje);
      });

      this.client.subscribe('/chat/'+this.proyecto+'/escribiendo', e => {
        this.escribiendo = e.body;
        setTimeout(() => this.escribiendo = '', 3000)

      });
      console.log(this.clienteId);
      this.client.subscribe('/chat/'+this.proyecto+'/historial/' + this.clienteId, e => {
        const historial = JSON.parse(e.body) as Mensaje[];
        this.mensajes = historial.map(m => {
          m.fecha = new Date(m.fecha);
          return m;
        }).reverse();
      });

      this.client.publish({ destination: '/app/'+this.proyecto+'/historial', body: this.clienteId });

      this.mensaje.tipo = 'NUEVO_USUARIO';
      this.client.publish({ destination: '/app/'+this.proyecto+'/mensaje', body: JSON.stringify(this.mensaje) });
    }

    this.client.onDisconnect = (frame) => {
      console.log('Desconectados: ' + !this.client.connected + ' : ' + frame);
      this.conectado = false;
      this.mensaje = new Mensaje();
      this.mensajes = [];
      this.mensaje.username = this.authService.usuario.username;
    }

  }

  conectar(): void {
    this.client.activate();
  }

  desconectar(): void {
    this.client.deactivate();
  }

  enviarMensaje(): void {
    console.log('El usuario: ' + this.mensaje.username + ' va a enviar el mensaje: '+this.mensaje.texto +
  ' con destino: '+ '/app/'+this.proyecto+'/mensaje' + ' y cuerpo: '+ JSON.stringify(this.mensaje));
    this.mensaje.tipo = 'MENSAJE';
    this.client.publish({ destination: '/app/'+this.proyecto+'/mensaje', body: JSON.stringify(this.mensaje) });
    this.mensaje.texto = '';
  }

  escribiendoEvento(): void {
    console.log('El usuario: ' + this.mensaje.username + ' esta escribiendo un mensaje.');
    this.client.publish({ destination: '/app/'+this.proyecto+'/escribiendo', body: this.mensaje.username });
  }
}
