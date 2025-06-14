import Shared

class SwiftLibDependencyFactory: SwiftLibDependencyFactoryContract {
  static var shared = SwiftLibDependencyFactory()
  
  func provideFirestoreRepository() -> any FirestoreRepository {
    return FirestoreRepositoryImpl()
  }
}
