import SwiftUI

struct RankingList: View {
  @StateObject private var viewModel = RankingViewModelObserver()
  
  var body: some View {
    List(viewModel.scores) { score in
      HStack {
        Text("\(score.score)")
        Text(score.userRef)
      }
    }
    .onAppear {
      viewModel.start()
    }
  }
}
