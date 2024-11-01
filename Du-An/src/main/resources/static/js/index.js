 // JavaScript để ẩn/hiện thanh tìm kiếm khi nhấn vào icon kính lúp
 document.getElementById('search-icon').addEventListener('click', function () {
    var searchForm = document.getElementById('mobile-search-form');
    if (searchForm.classList.contains('d-none')) {
      searchForm.classList.remove('d-none');
    } else {
      searchForm.classList.add('d-none');
    }
  });