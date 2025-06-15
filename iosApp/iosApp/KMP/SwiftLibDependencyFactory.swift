import Shared

class SwiftLibDependencyFactory: SwiftLibDependencyFactoryContract {
  static var shared = SwiftLibDependencyFactory()
  
  func provideFirestoreDataSource() -> any FirestoreDataSourceContract {
    return FirestoreDataSource()
  }
}
