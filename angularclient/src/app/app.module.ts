import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';
import {ChartsModule} from 'ng2-charts';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AddMitarbeiterFormComponent} from './components/mitarbeiter/add-mitarbeiter-form.component';
import {MitarbeiterService} from "./services/mitarbeiter/mitarbeiter.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {TableMitarbeiterComponent} from './components/mitarbeiter/table-mitarbeiter.component';
import {SearchMitarbeiterComponent} from './components/mitarbeiter/search-mitarbeiter.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatTableModule} from "@angular/material/table";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatInputModule} from "@angular/material/input";
import {MAT_DATE_LOCALE, MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {UpdateMitarbeiterFormComponent} from './components/mitarbeiter/update-mitarbeiter-form.component';
import {MatDividerModule} from "@angular/material/divider";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {TableVertriebComponent} from './components/vertrieb/table-vertrieb.component';
import {AddVertriebComponent} from './components/vertrieb/add-vertrieb.component';
import {UpdateVertriebComponent} from './components/vertrieb/update-vertrieb.component';
import {UpdateEinsatzComponent} from './components/einsatz/update-einsatz.component';
import {SearchEinsatzComponent} from './components/einsatz/search-einsatz.component';
import {TableEinsatzComponent} from './components/einsatz/table-einsatz.component';
import {AddEinsatzComponent} from './components/einsatz/add-einsatz.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatTabsModule} from "@angular/material/tabs";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {DeleteEinsatzDialogComponent} from './components/einsatz/delete-einsatz-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {DeleteMitarbeiterDialogComponent} from './components/mitarbeiter/delete-mitarbeiter-dialog.component';
import {DeleteVertriebDialogComponent} from './components/vertrieb/delete-vertrieb-dialog.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import { LoginComponent } from './components/login/login.component';
import {LoginService} from "./services/login/login.service";
import {BarChartComponent} from './components/mitarbeiter/bar-chart/bar-chart.component';
import {DoughnutChartComponent} from './components/mitarbeiter/doughnut-chart/doughnut-chart.component';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}
@NgModule({
  declarations: [
    AppComponent,
    AddMitarbeiterFormComponent,
    TableMitarbeiterComponent,
    SearchMitarbeiterComponent,
    UpdateMitarbeiterFormComponent,
    TableVertriebComponent,
    AddVertriebComponent,
    UpdateVertriebComponent,
    UpdateEinsatzComponent,
    SearchEinsatzComponent,
    TableEinsatzComponent,
    AddEinsatzComponent,
    DeleteEinsatzDialogComponent,
    DeleteMitarbeiterDialogComponent,
    DeleteVertriebDialogComponent,
    LoginComponent,
    BarChartComponent,
    DoughnutChartComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        NoopAnimationsModule,
        MatCardModule,
        MatButtonModule,
        MatMenuModule,
        MatTableModule,
        MatFormFieldModule,
        MatSortModule,
        MatPaginatorModule,
        MatInputModule,
        ReactiveFormsModule,
        MatOptionModule,
        MatSelectModule,
        MatDividerModule,
        MatSidenavModule,
        MatListModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatTabsModule,
        MatDialogModule,
        MatAutocompleteModule,
        ChartsModule
    ],

  providers: [MitarbeiterService,
    {provide: MAT_DATE_LOCALE, useValue: 'de'},
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    LoginService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
