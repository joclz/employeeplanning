import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Einsatz} from "../../models/einsatz";
import {EinsatzStatus} from "../../models/einsatz-status.enum";
import {EinsatzService} from "../../services/einsatz.service";
import {Mitarbeiter} from "../../models/mitarbeiter";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {MitarbeiterVertriebService} from "../../services/mitarbeiter-vertrieb.service";
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-add-einsatz',
  templateUrl: './add-einsatz.component.html',
  styleUrls: ['./add-einsatz.component.css']
})
export class AddEinsatzComponent implements OnInit {

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  mitarbeiter = new FormControl('', [Validators.required]);
  mitarbeiterVertrieb = new FormControl('', [Validators.required]);
  einsatzStatus = new FormControl('', [Validators.required]);
  beginn = new FormControl('', [Validators.required]);
  ende = new FormControl('', [Validators.required]);
  wahrscheinlichkeit = new FormControl('', [Validators.required]);
  zusatzkostenReise = new FormControl('', [Validators.required]);
  stundensatzVK = new FormControl('', [Validators.required]);
  projektnummerNettime = new FormControl('', [Validators.required]);
  beauftragungsnummer = new FormControl('', [Validators.required]);
  // deckungsbeitrag = new FormControl('', [Validators.required]);
  // marge = new FormControl('', [Validators.required]);

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
    let einsatz = new Einsatz();
    einsatz.mitarbeiter = this.mitarbeiterMap.get(this.mitarbeiter.value);
    einsatz.mitarbeiterVertrieb = this.mitarbeiterVertriebMap.get(this.mitarbeiterVertrieb.value);
    einsatz.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatz.beginn = this.beginn.value;
    einsatz.ende = this.ende.value;
    einsatz.wahrscheinlichkeit = this.wahrscheinlichkeit.value;
    einsatz.zusatzkosten = this.zusatzkostenReise.value;
    einsatz.stundensatzVK = this.stundensatzVK.value;
    einsatz.projektnummerNettime = this.projektnummerNettime.value;
    einsatz.beauftragungsnummer = this.beauftragungsnummer.value;
    // einsatz.deckungsbeitrag = this.deckungsbeitrag.value;
    // einsatz.marge = this.marge.value;

    this.einsatzService.save(einsatz).subscribe(() =>
      this.formRef.resetForm()
    );
  }

  ngOnInit(): void {
    this.mitarbeiterService.findAll().subscribe(result => {
      this.mitarbeiterList = result;
      this.mitarbeiterList.forEach( (mitarbeiter) => {
        this.mitarbeiterName.set(mitarbeiter.id, mitarbeiter.name + ', ' + mitarbeiter.vorname);
        this.mitarbeiterMap.set(mitarbeiter.id, mitarbeiter);
        this.mitarbeiterIds.push(mitarbeiter.id);
      });
    });

    this.mitarbeiterVertriebService.findAll().subscribe(result => {
      this.mitarbeiterVertriebList = result;
      this.mitarbeiterVertriebList.forEach( (mitarbeiterVertrieb) => {
        this.mitarbeiterVertriebName.set(mitarbeiterVertrieb.id, mitarbeiterVertrieb.name + ', ' + mitarbeiterVertrieb.vorname);
        this.mitarbeiterVertriebMap.set(mitarbeiterVertrieb.id, mitarbeiterVertrieb);
        this.mitarbeiterVertriebIds.push(mitarbeiterVertrieb.id);
      });
    });


    this.formGroup = new FormGroup({
      mitarbeiter: this.mitarbeiter,
      mitarbeiterVertrieb: this.mitarbeiterVertrieb,
      einsatzStatus: this.einsatzStatus,
      beginn: this.beginn,
      ende: this.ende,
      wahrscheinlichkeit: this.wahrscheinlichkeit,
      zusatzkosten: this.zusatzkostenReise,
      stundensatzVK: this.stundensatzVK,
      projektnummerNettime: this.projektnummerNettime,
      beauftragungsnummer: this.beauftragungsnummer
      // deckungsbeitrag: this.deckungsbeitrag,
      // marge: this.marge
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }

}
