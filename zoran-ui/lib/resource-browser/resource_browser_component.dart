import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:zoran.io/resource-browser/resource-view-minified/resource_view_minifed_component.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: 'resource_browser_component',
  styleUrls: const ['resource_browser_component.scss.css'],
  templateUrl: 'resource_browser_component.html',
  directives: const [
    coreDirectives,
    ResourceMinifiedComponent,
    MaterialTreeComponent,
    coreDirectives,
    MaterialProgressComponent,
    MaterialButtonComponent,
    MaterialTreeDropdownComponent,
    MaterialChipsComponent,
    MaterialChipComponent,
    displayNameRendererDirective,
  ],
  providers: const [
    materialProviders,
  ],
)
class ResourceBrowserComponent implements OnInit {

  final NewResourceService service;
  final ZoranService zoranService;
  List<ResourceResponse> _resources;

  ResourceBrowserComponent(this.zoranService, this.service);
  List<ResourceResponse> get resources => _resources;

  @Input("resourceMode")
  bool isInResourceMode = true;

  @override
  Future ngOnInit() async {
    if(isInResourceMode) {
      _resources = await zoranService.getResources();
    } else {
      _resources = await zoranService.getTemplates();
    }
  }
}
