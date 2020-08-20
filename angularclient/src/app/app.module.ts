import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MitarbeiterFormComponent} from './components/mitarbeiter/mitarbeiter-form.component';
import {MitarbeiterService} from "./services/mitarbeiter.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MitarbeiterComponent} from './components/mitarbeiter/mitarbeiter.component';
import {MitarbeiterSearchComponent} from './components/mitarbeiter/mitarbeiter-search.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    MitarbeiterFormComponent,
    MitarbeiterComponent,
    MitarbeiterSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NoopAnimationsModule
  ],
  providers: [MitarbeiterService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
