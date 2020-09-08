import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Einsatz} from "../../models/einsatz";
import {EinsatzService} from "../../services/einsatz.service";
import {EinsatzStatus} from "../../models/einsatz-status.enum";
import {EinsatzSuche} from "../../models/einsatz-suche";
import {MitarbeiterStatus} from "../../models/mitarbeiter-status.enum";

const patternId = Validators.pattern('^\\d+$');

@Component({
  selector: 'app-search-einsatz',
  templateUrl: './search-einsatz.component.html',
  styleUrls: ['./search-einsatz.component.css']
})
export class SearchEinsatzComponent implements OnInit, OnDestroy {

  mitarbeiterVertrieb = new FormControl('');
  mitarbeiter = new FormControl('');
  mitarbeiterStatus = new FormControl('');
  einsatzStatus = new FormControl('');
  beginnVon = new FormControl('');
  beginnBis = new FormControl('');
  endeVon = new FormControl('');
  endeBis = new FormControl('');
  searchFormGroup: FormGroup;

  mitarbeiterVertriebId = new FormControl('', [Validators.required, patternId]);
  searchMitarbeiterVertriebFormGroup: FormGroup;

  einsatzStatusEnum = EinsatzStatus;
  einsatzStatusList = [];

  mitarbeiterStatusEnum = MitarbeiterStatus;
  mitarbeiterStatusList = [];

  isSearch: boolean = false;
  isMitarbeiterVertrieb: boolean = false;
  isEinsatzAngeboten: boolean = false;
  isEinsatzBeauftragt: boolean = false;
  isEinsatzAbgelehnt: boolean = false;

  einsaetzeAngebotenList: Einsatz[];
  einsaetzeBeauftragtList: Einsatz[];
  einsaetzeAbgelehntList: Einsatz[];
  einsaetzeMitarbeiterVertriebList: Einsatz[];
  einsaetzeSearchList: Einsatz[];

  constructor(private route: ActivatedRoute, private router: Router, private einsatzService: EinsatzService) {
    this.einsatzStatusList = Object.keys(this.einsatzStatusEnum);
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatusEnum);
  }

  ngOnInit(): void {
    this.searchFormGroup = new FormGroup({
      mitarbeiterVertrieb: this.mitarbeiterVertrieb,
      mitarbeiter: this.mitarbeiter,
      mitarbeiterStatus: this.mitarbeiterStatus,
      einsatzStatus: this.einsatzStatus,
      beginnVon: this.beginnVon,
      beginnBis: this.beginnBis,
      endeVon: this.endeVon,
      endeBis: this.endeBis
    });

    this.searchMitarbeiterVertriebFormGroup = new FormGroup({
      mitarbeiterVertriebId: this.mitarbeiterVertriebId,
    });

    this.initFirstTab();
  }

  search() {
    this.isSearch = false;

    let einsatzSuche = new EinsatzSuche();
    einsatzSuche.mitarbeiterVertriebId = this.mitarbeiterVertrieb.value;
    einsatzSuche.mitarbeiterId = this.mitarbeiter.value;
    einsatzSuche.mitarbeiterStatus = <MitarbeiterStatus>this.mitarbeiterStatus.value;
    einsatzSuche.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatzSuche.beginnVon = this.beginnVon.value;
    einsatzSuche.beginnBis = this.beginnBis.value;
    einsatzSuche.endeVon = this.endeVon.value;
    einsatzSuche.endeBis = this.endeBis.value;
    this.einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche).subscribe(result => {
      this.einsaetzeSearchList = result;
      this.isSearch = true;
    });
  }

  searchMitarbeiterVertrieb() {
    this.isMitarbeiterVertrieb = false;
    this.einsatzService.findEinsaetzeByMitarbeiterVertrieb(this.mitarbeiterVertriebId.value).subscribe(result => {
      this.einsaetzeMitarbeiterVertriebList = result;
      this.isMitarbeiterVertrieb = true;
    });
  }

  ngOnDestroy(): void {
    this.isSearch = false;
    this.isMitarbeiterVertrieb = false;
    this.isEinsatzAngeboten = false;
    this.isEinsatzBeauftragt = false;
    this.isEinsatzAbgelehnt = false;
  }

  selectTab(index: number) {
    if (index === 0) {
      this.initFirstTab();
    }
    if (index === 1) {
      this.einsatzService.findEinsaetzeByEinsatzStatus(this.getEinsatzStatus(EinsatzStatus.BEAUFTRAGT)).subscribe(result => {
        this.einsaetzeBeauftragtList = result;
        this.isEinsatzBeauftragt = true;
      });
    }
    if (index === 2) {
      this.einsatzService.findEinsaetzeByEinsatzStatus(this.getEinsatzStatus(EinsatzStatus.ABGELEHNT)).subscribe(result => {
        this.einsaetzeAbgelehntList = result;
        this.isEinsatzAbgelehnt = true;
      });
    }
  }

  private getEinsatzStatus(einsatzStatus: EinsatzStatus): EinsatzStatus {
    return this.einsatzStatusList.filter(x => this.einsatzStatusEnum[x] == einsatzStatus)[0];
  }

  private initFirstTab() {
    this.einsatzService.findEinsaetzeByEinsatzStatus(this.getEinsatzStatus(EinsatzStatus.ANGEBOTEN)).subscribe(result => {
      this.einsaetzeAngebotenList = result;
      this.isEinsatzAngeboten = true;
    });
  }
}
