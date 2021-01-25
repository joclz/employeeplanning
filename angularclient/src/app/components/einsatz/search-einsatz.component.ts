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
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-search-einsatz',
  templateUrl: './search-einsatz.component.html',
  styleUrls: ['./search-einsatz.component.css']
})
export class SearchEinsatzComponent implements OnInit, OnDestroy {

  filteredMitarbeiter: Observable<MitarbeiterDTO[]>;
  filteredMitarbeiterVertriebSearch: Observable<MitarbeiterDTO[]>;
  mitarbeiterVertrieb = new FormControl('');
  mitarbeiter = new FormControl('');
  mitarbeiterStatus = new FormControl('');
  einsatzStatus = new FormControl('');
  beginnVon = new FormControl('');
  beginnBis = new FormControl('');
  endeVon = new FormControl('');
  endeBis = new FormControl('');
  wahrscheinlichkeitVon = new FormControl('');
  wahrscheinlichkeitBis = new FormControl('');
  searchFormGroup: FormGroup;

  filteredMitarbeiterVertrieb: Observable<MitarbeiterDTO[]>;
  einsatzMitarbeiterVertrieb = new FormControl('', [Validators.required]);
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
  einsaetzeFilter: String[];

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

      this.filteredMitarbeiter = this.getMitarbeiterFilteredOptions(this.mitarbeiter, this.mitarbeiterList);
    });

    this.mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName().subscribe(result => {
      this.mitarbeiterVertriebList = result;

      this.filteredMitarbeiterVertrieb = this.getMitarbeiterFilteredOptions(this.einsatzMitarbeiterVertrieb, this.mitarbeiterVertriebList);
      this.filteredMitarbeiterVertriebSearch = this.getMitarbeiterFilteredOptions(this.mitarbeiterVertrieb, this.mitarbeiterVertriebList);
    });

    this.searchFormGroup = new FormGroup({
      mitarbeiterVertrieb: this.mitarbeiterVertrieb,
      mitarbeiter: this.mitarbeiter,
      mitarbeiterStatus: this.mitarbeiterStatus,
      einsatzStatus: this.einsatzStatus,
      beginnVon: this.beginnVon,
      beginnBis: this.beginnBis,
      endeVon: this.endeVon,
      endeBis: this.endeBis,
      wahrscheinlichkeitVon: this.wahrscheinlichkeitVon,
      wahrscheinlichkeitBis: this.wahrscheinlichkeitBis
    });

    this.searchMitarbeiterVertriebFormGroup = new FormGroup({
      einsatzMitarbeiterVertrieb: this.einsatzMitarbeiterVertrieb,
    });

    this.initFirstTab();
  }

  private getMitarbeiterFilteredOptions(formControl: FormControl, mitarbeiterList: Array<MitarbeiterDTO>): Observable<MitarbeiterDTO[]> {
    return formControl.valueChanges.pipe(
      startWith(''),
      map(value => typeof value === "string" ? value : value ? value.name : ''),
      map(name => name ? this._filterMitarbeiter(name, mitarbeiterList) : mitarbeiterList.slice())
    );
  }

  private _filterMitarbeiter(value: string, mitarbeiterList: Array<MitarbeiterDTO>): MitarbeiterDTO[] {
    const filterValue = value.toLowerCase();
    return mitarbeiterList.filter(mitarbeiter => mitarbeiter.name.toLowerCase().indexOf(filterValue) === 0);
  }

  displayFn(mitarbeiter: MitarbeiterDTO): string {
    return mitarbeiter && mitarbeiter.name ? mitarbeiter.name : '';
  }

  search() {
    this.isSearch = false;

    let einsatzSucheDTO = this.initEinsatzSucheDTO();

    this.einsaetzeFilter = this.einsatzService.writeEinsaetzeFilter(einsatzSucheDTO);

    this.einsatzService.findEinsaetzeBySuchkriterien(einsatzSucheDTO).subscribe(result => {
      this.einsaetzeSearchList = result;
      this.isSearch = true;
    });
  }

  initEinsatzSucheDTO(): EinsatzSucheDto {
    let einsatzSucheDTO = new EinsatzSucheDto();
    einsatzSucheDTO.mitarbeiterVertriebId = this.mitarbeiterVertrieb.value ? this.mitarbeiterVertrieb.value.id : '';
    einsatzSucheDTO.mitarbeiterId = this.mitarbeiter.value ? this.mitarbeiter.value.id : '';
    einsatzSucheDTO.mitarbeiterStatus = <MitarbeiterStatus>this.mitarbeiterStatus.value;
    einsatzSucheDTO.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatzSucheDTO.beginnVon = this.beginnVon.value;
    einsatzSucheDTO.beginnBis = this.beginnBis.value;
    einsatzSucheDTO.endeVon = this.endeVon.value;
    einsatzSucheDTO.endeBis = this.endeBis.value;
    einsatzSucheDTO.wahrscheinlichkeitVon = this.wahrscheinlichkeitVon.value;
    einsatzSucheDTO.wahrscheinlichkeitBis = this.wahrscheinlichkeitBis.value;
    return einsatzSucheDTO;
  }

  searchMitarbeiterVertrieb() {
    this.isMitarbeiterVertrieb = false;
    this.einsatzService.findEinsaetzeByMitarbeiterVertrieb(this.einsatzMitarbeiterVertrieb.value.id).subscribe(result => {
      this.einsaetzeMitarbeiterVertriebList = result;
      this.isMitarbeiterVertrieb = true;

      this.einsatzMitarbeiterVertrieb.reset();
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
      let einsatzSucheDTO = this.initEinsatzSucheDTO();
      einsatzSucheDTO.einsatzStatus = EinsatzStatus.BEAUFTRAGT;
      this.einsaetzeFilter = this.einsatzService.writeEinsaetzeFilter(einsatzSucheDTO);

      this.einsatzService.findEinsaetzeByEinsatzStatus(this.getEinsatzStatus(EinsatzStatus.BEAUFTRAGT)).subscribe(result => {
        this.einsaetzeBeauftragtList = result;
        this.isEinsatzBeauftragt = true;
      });
    }
    if (index === 2) {
      let einsatzSucheDTO = this.initEinsatzSucheDTO();
      einsatzSucheDTO.einsatzStatus = EinsatzStatus.ABGELEHNT;
      this.einsaetzeFilter = this.einsatzService.writeEinsaetzeFilter(einsatzSucheDTO);

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
    let einsatzSucheDTO = this.initEinsatzSucheDTO();
    einsatzSucheDTO.einsatzStatus = EinsatzStatus.ANGEBOTEN;
    this.einsaetzeFilter = this.einsatzService.writeEinsaetzeFilter(einsatzSucheDTO);

    this.einsatzService.findEinsaetzeByEinsatzStatus(this.getEinsatzStatus(EinsatzStatus.ANGEBOTEN)).subscribe(result => {
      this.einsaetzeAngebotenList = result;
      this.isEinsatzAngeboten = true;
    });
  }

  resetForm() {
    this.searchFormGroup.reset();
  }
}
