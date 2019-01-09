import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: "renderer",
  template: '<div><b>{{depModel.id}} {{depModel.name}}</b><br> {{depModel.description}}</div>',
  directives: const [
    coreDirectives
  ],
)
class DependencyRenderer implements RendersValue<LanguageDependenciesModel>{
  LanguageDependenciesModel depModel;
  @override
  set value(LanguageDependenciesModel newValue) {
    depModel = newValue;
  }
}