import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzService} from "../../services/einsatz/einsatz.service";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzSucheDto} from "../../models/einsatz/einsatz-suche-dto";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {MitarbeiterVertriebService} from "../../services/vertrieb/mitarbeiter-vertrieb.service";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";

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

  mitarbeiterVertriebId = new FormControl('', [Validators.required]);
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

  mitarbeiterList: Array<MitarbeiterDTO>;

  mitarbeiterVertriebList: Array<MitarbeiterDTO>;

  constructor(private route: ActivatedRoute, private router: Router, private einsatzService: EinsatzService,
              private mitarbeiterService: MitarbeiterService,
              private mitarbeiterVertriebService: MitarbeiterVertriebService) {
    this.einsatzStatusList = Object.keys(this.einsatzStatusEnum);
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatusEnum);
  }

  ngOnInit(): void {
    this.mitarbeiterService.getMitarbeiterListOrderByName().subscribe(result => {
      this.mitarbeiterList = result;
    });

    this.mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName().subscribe(result => {
      this.mitarbeiterVertriebList = result;
    });

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

    let einsatzSucheDTO = new EinsatzSucheDto();
    einsatzSucheDTO.mitarbeiterVertriebId = this.mitarbeiterVertrieb.value;
    einsatzSucheDTO.mitarbeiterId = this.mitarbeiter.value;
    einsatzSucheDTO.mitarbeiterStatus = <MitarbeiterStatus>this.mitarbeiterStatus.value;
    einsatzSucheDTO.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatzSucheDTO.beginnVon = this.beginnVon.value;
    einsatzSucheDTO.beginnBis = this.beginnBis.value;
    einsatzSucheDTO.endeVon = this.endeVon.value;
    einsatzSucheDTO.endeBis = this.endeBis.value;
    this.einsatzService.findEinsaetzeBySuchkriterien(einsatzSucheDTO).subscribe(result => {
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

  resetForm() {
    this.searchFormGroup.reset();
  }
}
