# NovaRetail storefront

Responsive React storefront and admin console for the NovaRetail Spring Boot API. The customer experience includes a landing page, authenticated product browsing with search/category/sort controls, sign-in and registration, and a cart view. Admin accounts can view store analytics, create/delete products, and create categories.

Admin access is determined from the role returned by `/auth/login`. Backend authorization remains authoritative; admin API calls are protected by Spring Security. The admin UI intentionally does not offer product editing (no update endpoint exists) or category deletion (the current entity cascade can remove related products).

## Run locally

1. Start the Spring Boot API from the repository root. Vite proxies frontend API requests to `http://localhost:8080`.
2. In this directory, run `npm install` once, then `npm run dev`.
3. Open the Vite URL shown in the terminal (usually `http://localhost:5173`).

To use a different API URL (for example, when deploying the frontend outside Vite), set `VITE_API_BASE_URL` in a `.env` file in this directory, for example:

```env
VITE_API_BASE_URL=http://localhost:8080
```

The Vite development proxy avoids browser CORS issues locally. Direct browser requests to the API require the origin to be allowed by the backend CORS configuration.

The catalog, account, and cart screens call the existing backend endpoints. Product photos and checkout/removal actions are not included because the current API does not expose those fields/actions.

## Checks

```sh
npm run lint
npm run build
```
