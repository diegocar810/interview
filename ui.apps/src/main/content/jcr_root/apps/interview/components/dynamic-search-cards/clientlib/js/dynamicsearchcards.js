let dynamicSearchCards = (function () {
    let selectors = {
        self: '[data-cmp-is="dynamicsearchcards"]',
        genre: '.dynamic-search-cards__genre',
        apiServlet: 'data-apiservlet',
        input: '.dynamic-search-cards__input',
        inputSearch: '.dynamic-search-cards__iconsearch',
        containerResults: '.dynamic-search-cards__container'
    }
    const component = document.querySelector(selectors.self);
    function initevents() {

        component.querySelectorAll(selectors.genre).forEach(genre => {
            genre.addEventListener('click', function (event) {
                const query = component.querySelector(selectors.input).value;
                if (event.target.classList.contains('selected')) {
                    callApi({ genre: 'all', query_term: query });
                } else {
                    event.target.classList.add('selected');
                    callApi({ genre: event.target.textContent, query_term: query });
                }
                component.querySelectorAll(selectors.genre).forEach(genre => {
                    if (!genre.isEqualNode(event.target)) {
                        genre.classList.remove('selected');
                    }
                });
            });
        });

        component.querySelector(selectors.inputSearch).addEventListener('click', function (event) {
            const query = component.querySelector(selectors.input).value;
            callApi({ genre: event.target.textContent, query_term: query });
        })
    }
    function callApi(query) {
        let url = new URL(component.getAttribute(selectors.apiServlet), window.location.origin);
        Object.keys(query).forEach(key => url.searchParams.append(key, query[key]));

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            }).then(res => {
                const movies = res.data?.movies
                let template = ""
                component.querySelector(selectors.containerResults).innerHTML = '';
                movies.forEach(movie => {
                    let genres = "";
                    movie.genres.forEach(genre => {
                        genres += `<span class="dynamic-search-cards__genre">${genre}</span>`
                    })
                    template += `<div class="dynamic-search-cards__card">
                                   <img src="${movie.large_cover_image}" alt="${movie.title}">
                                   <div class="dynamic-search-cards__card-content">
                                    <h2>${movie.title}</h2>
                                    <p class="dynamic-search-cards__description">${movie.summary}</p>
                                     ${genres}
                                   </div>
                                  </div>
                                `
                });
                component.querySelector(selectors.containerResults).insertAdjacentHTML("afterbegin", template);
            }).catch(error => {
                console.error('Error:', error)
            });
    }

    function init() {
        initevents();
        callApi({ genre: 'all', query_term: "0" });
    }
    return { init: init }
})()

dynamicSearchCards.init();