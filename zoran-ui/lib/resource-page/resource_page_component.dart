import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/401/401_page_component.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/user_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: 'resources',
  templateUrl: 'resource_page_component.html',
  directives: const [
    coreDirectives,
    routerDirectives,
    MaterialIconComponent,
    MaterialButtonComponent,
    UnauthorizedComponent
  ],
  styleUrls: const ['resource_page_component.scss.css'],
  providers: const [
    materialProviders,
    const ClassProvider(ZoranService),
  ],
)
class ResourcePageComponent implements OnInit {
  final Router router;
  final ZoranService _zoranService;
  final NewResourceService _newResourceService;
  final UserService _userService;
  List<ResourceResponse> moduleList;

  ResourcePageComponent(this.router, this._zoranService,
      this._newResourceService, this._userService);

  void navigate(String url) {
    final urls = RoutePaths.resource.toUrl(parameters: {uriParam: '$url'});
    router.navigate(urls);
  }

  void createNewResource() {
    _newResourceService.createNewRequest();
    router.navigate('new-resource');
  }

  @override
  Future ngOnInit() async {
    moduleList = await _zoranService.getResources();

    if (_userService.isAuthenticated()) {
      moduleList.add(new ResourceResponse(
          null,
          "Create New!",
          null,
          null,
          null,
          ResourceType.NEW,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          [],
          [])
      );
    }
  }

  String getStateForResource(ResourceResponse dto) {
    return dto.type.toString();
  }

  ResourceType get newType => ResourceType.NEW;
}