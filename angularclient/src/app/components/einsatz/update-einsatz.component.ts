import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Einsatz} from "../../models/einsatz";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {Mitarbeiter} from "../../models/mitarbeiter";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {EinsatzStatus} from "../../models/einsatz-status.enum";
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";
import {EinsatzService} from "../../services/einsatz.service";
import {MitarbeiterVertriebService} from "../../services/mitarbeiter-vertrieb.service";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-update-einsatz',
  templateUrl: './update-einsatz.component.html',
  styleUrls: ['./update-einsatz.component.css']
})
export class UpdateEinsatzComponent implements OnInit {

  @Input() einsatzInput: Einsatz;
  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  id = new FormControl({value: '', disabled: true});
  mitarbeiter = new FormControl('', [Validators.required]);
  mitarbeiterVertrieb = new FormControl('', [Validators.required]);
  einsatzStatus = new FormControl('', [Validators.required]);
  beginn = new FormControl('', [Validators.required]);
  ende = new FormControl('', [Validators.required]);

  //TODO Validatoren?
  wahrscheinlichkeit = new FormControl('', [Validators.required]);
  zusatzkostenReise = new FormControl('', [Validators.required]);
  stundensatzVK = new FormControl('', [Validators.required]);
  projektnummerNettime = new FormControl('', [Validators.required]);
  beauftragungsnummer = new FormControl('', [Validators.required]);

  einsatzStatusEnum = EinsatzStatus;
  einsatzStatusList = [];

  mitarbeiterList: Array<Mitarbeiter>;
  mitarbeiterName = new Map();
  mitarbeiterIds = [];
  mitarbeiterMap = new Map();

  mitarbeiterVertriebList: Array<MitarbeiterVertrieb>;
  mitarbeiterVertriebName = new Map();
  mitarbeiterVertriebIds = [];
  mitarbeiterVertriebMap = new Map();

  constructor(private route: ActivatedRoute, private router: Router,
              private einsatzService: EinsatzService,
              private mitarbeiterService: MitarbeiterService,
              private mitarbeiterVertriebService: MitarbeiterVertriebService) {
    this.einsatzStatusList = Object.keys(this.einsatzStatusEnum);
  }

  onSubmit() {
    //TODO Weiterleitung nach Update? Oder nur Meldung ausgeben?

    let einsatz = new Einsatz();
    einsatz.id = this.id.value;
    einsatz.mitarbeiter = this.mitarbeiterMap.get(this.mitarbeiter.value);
    einsatz.mitarbeiterVertrieb = this.mitarbeiterVertriebMap.get(this.mitarbeiterVertrieb.value);
    einsatz.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatz.beginn = this.beginn.value;
    einsatz.ende = this.ende.value;
    einsatz.wahrscheinlichkeit = this.wahrscheinlichkeit.value;
    einsatz.zusatzkostenReise = this.zusatzkostenReise.value;
    einsatz.stundensatzVK = this.stundensatzVK.value;
    einsatz.projektnummerNettime = this.projektnummerNettime.value;
    einsatz.beauftragungsnummer = this.beauftragungsnummer.value;

    this.einsatzService.update(einsatz).subscribe();
  }

  ngOnInit(): void {
    this.mitarbeiterService.findAll().subscribe(result => {
      this.mitarbeiterList = result;
      this.mitarbeiterList.forEach((mitarbeiter) => {
        this.mitarbeiterName.set(mitarbeiter.id, mitarbeiter.name + ', ' + mitarbeiter.vorname);
        this.mitarbeiterMap.set(mitarbeiter.id, mitarbeiter);
        this.mitarbeiterIds.push(mitarbeiter.id);
      });
    });

    this.mitarbeiterVertriebService.findAll().subscribe(result => {
      this.mitarbeiterVertriebList = result;
      this.mitarbeiterVertriebList.forEach((mitarbeiterVertrieb) => {
        this.mitarbeiterVertriebName.set(mitarbeiterVertrieb.id, mitarbeiterVertrieb.name + ', ' + mitarbeiterVertrieb.vorname);
        this.mitarbeiterVertriebMap.set(mitarbeiterVertrieb.id, mitarbeiterVertrieb);
        this.mitarbeiterVertriebIds.push(mitarbeiterVertrieb.id);
      });
    });

    this.id.setValue(this.einsatzInput.id);
    this.mitarbeiter.setValue(this.einsatzInput.mitarbeiter.id);
    this.mitarbeiterVertrieb.setValue(this.einsatzInput.mitarbeiterVertrieb.id);
    this.einsatzStatus.setValue(this.einsatzInput.einsatzStatus);
    this.beginn.setValue(this.einsatzInput.beginn);
    this.ende.setValue(this.einsatzInput.ende);
    this.wahrscheinlichkeit.setValue(this.einsatzInput.wahrscheinlichkeit);
    this.zusatzkostenReise.setValue(this.einsatzInput.zusatzkostenReise);
    this.stundensatzVK.setValue(this.einsatzInput.stundensatzVK);
    this.projektnummerNettime.setValue(this.einsatzInput.projektnummerNettime);
    this.beauftragungsnummer.setValue(this.einsatzInput.beauftragungsnummer);

    this.formGroup = new FormGroup({
      id: this.id,
      mitarbeiter: this.mitarbeiter,
      mitarbeiterVertrieb: this.mitarbeiterVertrieb,
      einsatzStatus: this.einsatzStatus,
      beginn: this.beginn,
      ende: this.ende,
      wahrscheinlichkeit: this.wahrscheinlichkeit,
      zusatzkostenReise: this.zusatzkostenReise,
      stundensatzVK: this.stundensatzVK,
      projektnummerNettime: this.projektnummerNettime,
      beauftragungsnummer: this.beauftragungsnummer
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }

}
