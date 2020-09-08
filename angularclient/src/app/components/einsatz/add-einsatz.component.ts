import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {EinsatzStatus} from "../../models/einsatz-status.enum";
import {EinsatzService} from "../../services/einsatz.service";
import {Mitarbeiter} from "../../models/mitarbeiter";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {MitarbeiterVertriebService} from "../../services/mitarbeiter-vertrieb.service";
import {MitarbeiterVertrieb} from "../../models/mitarbeiter-vertrieb";
import {EinsatzDTO} from "../../models/einsatz-dto";

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

  einsatzStatusEnum = EinsatzStatus;
  einsatzStatusList = [];

  mitarbeiterList: Array<Mitarbeiter>;
  mitarbeiterName = new Map();
  mitarbeiterIds = [];

  mitarbeiterVertriebList: Array<MitarbeiterVertrieb>;
  mitarbeiterVertriebName = new Map();
  mitarbeiterVertriebIds = [];

  constructor(private route: ActivatedRoute, private router: Router,
              private einsatzService: EinsatzService,
              private mitarbeiterService: MitarbeiterService,
              private mitarbeiterVertriebService: MitarbeiterVertriebService) {
    this.einsatzStatusList = Object.keys(this.einsatzStatusEnum);
  }

  onSubmit() {
    let einsatzDTO = new EinsatzDTO();
    einsatzDTO.mitarbeiterId = this.mitarbeiter.value;
    einsatzDTO.mitarbeiterVertriebId = this.mitarbeiterVertrieb.value;
    einsatzDTO.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatzDTO.beginn = this.beginn.value;
    einsatzDTO.ende = this.ende.value;
    einsatzDTO.wahrscheinlichkeit = this.wahrscheinlichkeit.value;
    einsatzDTO.zusatzkostenReise = this.zusatzkostenReise.value;
    einsatzDTO.stundensatzVK = this.stundensatzVK.value;
    einsatzDTO.projektnummerNettime = this.projektnummerNettime.value;
    einsatzDTO.beauftragungsnummer = this.beauftragungsnummer.value;

    this.einsatzService.save(einsatzDTO).subscribe(() =>
      this.formRef.resetForm()
    );
  }

  ngOnInit(): void {
    this.mitarbeiterService.findAll().subscribe(result => {
      this.mitarbeiterList = result;
      this.mitarbeiterList.forEach( (mitarbeiter) => {
        this.mitarbeiterName.set(mitarbeiter.id, mitarbeiter.name + ', ' + mitarbeiter.vorname);
        this.mitarbeiterIds.push(mitarbeiter.id);
      });
    });

    this.mitarbeiterVertriebService.findAll().subscribe(result => {
      this.mitarbeiterVertriebList = result;
      this.mitarbeiterVertriebList.forEach( (mitarbeiterVertrieb) => {
        this.mitarbeiterVertriebName.set(mitarbeiterVertrieb.id, mitarbeiterVertrieb.name + ', ' + mitarbeiterVertrieb.vorname);
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
