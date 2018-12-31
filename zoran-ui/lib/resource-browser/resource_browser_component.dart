import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:sheets_dashboard/resource-browser/resource-view-minified/resource_view_minifed_component.dart';
import 'package:sheets_dashboard/zoran_service.dart';

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
  @Input()
  ProjectDetails details;

  final ZoranService zoranService;
  List<ProjectDetails> _resources;
  ResourceBrowserComponent(this.zoranService);
  List<ProjectDetails> get resources => _resources;

  @override
  Future ngOnInit() async {
    _resources = await zoranService.getAvailableResource();
  }
}
