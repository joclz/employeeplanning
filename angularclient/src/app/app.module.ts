import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MitarbeiterListComponent} from './mitarbeiter-list/mitarbeiter-list.component';
import {MitarbeiterFormComponent} from './mitarbeiter-form/mitarbeiter-form.component';
import {MitarbeiterService} from "./mitarbeiter.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    MitarbeiterListComponent,
    MitarbeiterFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [MitarbeiterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
