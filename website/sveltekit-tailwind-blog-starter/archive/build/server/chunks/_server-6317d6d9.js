import { e as error } from './index2-78bfc04e.js';
import { c as config } from './config-85e7a33b.js';
import { p as private_env } from './shared-server-a6895406.js';

var commonjsGlobal = typeof globalThis !== 'undefined' ? globalThis : typeof window !== 'undefined' ? window : typeof global !== 'undefined' ? global : typeof self !== 'undefined' ? self : {};

function getDefaultExportFromCjs (x) {
	return x && x.__esModule && Object.prototype.hasOwnProperty.call(x, 'default') ? x['default'] : x;
}

var base64$1 = {exports: {}};

/*! https://mths.be/base64 v1.0.0 by @mathias | MIT license */
base64$1.exports;

var hasRequiredBase64;

function requireBase64 () {
	if (hasRequiredBase64) return base64$1.exports;
	hasRequiredBase64 = 1;
	(function (module, exports) {
(function(root) {

			// Detect free variables `exports`.
			var freeExports = exports;

			// Detect free variable `module`.
			var freeModule = module &&
				module.exports == freeExports && module;

			// Detect free variable `global`, from Node.js or Browserified code, and use
			// it as `root`.
			var freeGlobal = typeof commonjsGlobal == 'object' && commonjsGlobal;
			if (freeGlobal.global === freeGlobal || freeGlobal.window === freeGlobal) {
				root = freeGlobal;
			}

			/*--------------------------------------------------------------------------*/

			var InvalidCharacterError = function(message) {
				this.message = message;
			};
			InvalidCharacterError.prototype = new Error;
			InvalidCharacterError.prototype.name = 'InvalidCharacterError';

			var error = function(message) {
				// Note: the error messages used throughout this file match those used by
				// the native `atob`/`btoa` implementation in Chromium.
				throw new InvalidCharacterError(message);
			};

			var TABLE = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
			// http://whatwg.org/html/common-microsyntaxes.html#space-character
			var REGEX_SPACE_CHARACTERS = /[\t\n\f\r ]/g;

			// `decode` is designed to be fully compatible with `atob` as described in the
			// HTML Standard. http://whatwg.org/html/webappapis.html#dom-windowbase64-atob
			// The optimized base64-decoding algorithm used is based on @atk’s excellent
			// implementation. https://gist.github.com/atk/1020396
			var decode = function(input) {
				input = String(input)
					.replace(REGEX_SPACE_CHARACTERS, '');
				var length = input.length;
				if (length % 4 == 0) {
					input = input.replace(/==?$/, '');
					length = input.length;
				}
				if (
					length % 4 == 1 ||
					// http://whatwg.org/C#alphanumeric-ascii-characters
					/[^+a-zA-Z0-9/]/.test(input)
				) {
					error(
						'Invalid character: the string to be decoded is not correctly encoded.'
					);
				}
				var bitCounter = 0;
				var bitStorage;
				var buffer;
				var output = '';
				var position = -1;
				while (++position < length) {
					buffer = TABLE.indexOf(input.charAt(position));
					bitStorage = bitCounter % 4 ? bitStorage * 64 + buffer : buffer;
					// Unless this is the first of a group of 4 characters…
					if (bitCounter++ % 4) {
						// …convert the first 8 bits to a single ASCII character.
						output += String.fromCharCode(
							0xFF & bitStorage >> (-2 * bitCounter & 6)
						);
					}
				}
				return output;
			};

			// `encode` is designed to be fully compatible with `btoa` as described in the
			// HTML Standard: http://whatwg.org/html/webappapis.html#dom-windowbase64-btoa
			var encode = function(input) {
				input = String(input);
				if (/[^\0-\xFF]/.test(input)) {
					// Note: no need to special-case astral symbols here, as surrogates are
					// matched, and the input is supposed to only contain ASCII anyway.
					error(
						'The string to be encoded contains characters outside of the ' +
						'Latin1 range.'
					);
				}
				var padding = input.length % 3;
				var output = '';
				var position = -1;
				var a;
				var b;
				var c;
				var buffer;
				// Make sure any padding is handled outside of the loop.
				var length = input.length - padding;

				while (++position < length) {
					// Read three bytes, i.e. 24 bits.
					a = input.charCodeAt(position) << 16;
					b = input.charCodeAt(++position) << 8;
					c = input.charCodeAt(++position);
					buffer = a + b + c;
					// Turn the 24 bits into four chunks of 6 bits each, and append the
					// matching character for each of them to the output.
					output += (
						TABLE.charAt(buffer >> 18 & 0x3F) +
						TABLE.charAt(buffer >> 12 & 0x3F) +
						TABLE.charAt(buffer >> 6 & 0x3F) +
						TABLE.charAt(buffer & 0x3F)
					);
				}

				if (padding == 2) {
					a = input.charCodeAt(position) << 8;
					b = input.charCodeAt(++position);
					buffer = a + b;
					output += (
						TABLE.charAt(buffer >> 10) +
						TABLE.charAt((buffer >> 4) & 0x3F) +
						TABLE.charAt((buffer << 2) & 0x3F) +
						'='
					);
				} else if (padding == 1) {
					buffer = input.charCodeAt(position);
					output += (
						TABLE.charAt(buffer >> 2) +
						TABLE.charAt((buffer << 4) & 0x3F) +
						'=='
					);
				}

				return output;
			};

			var base64 = {
				'encode': encode,
				'decode': decode,
				'version': '1.0.0'
			};

			// Some AMD build optimizers, like r.js, check for specific condition patterns
			// like the following:
			if (freeExports && !freeExports.nodeType) {
				if (freeModule) { // in Node.js or RingoJS v0.8.0+
					freeModule.exports = base64;
				} else { // in Narwhal or RingoJS v0.7.0-
					for (var key in base64) {
						base64.hasOwnProperty(key) && (freeExports[key] = base64[key]);
					}
				}
			} else { // in Rhino or a web browser
				root.base64 = base64;
			}

		}(commonjsGlobal)); 
	} (base64$1, base64$1.exports));
	return base64$1.exports;
}

var base64Exports = requireBase64();
var base64 = /*@__PURE__*/getDefaultExportFromCjs(base64Exports);

async function registerEmail$6(email) {
  const BUTTONDOWN_API_KEY = private_env.BUTTONDOWN_API_KEY;
  const BUTTONDOWN_API_URL = private_env.BUTTONDOWN_API_URL;
  try {
    const API_ROUTE = `${BUTTONDOWN_API_URL}subscribers`;
    const response = await fetch(API_ROUTE, {
      body: JSON.stringify({ email }),
      headers: { Authorization: `Token ${BUTTONDOWN_API_KEY}`, "Content-Type": "application/json" },
      method: "POST"
    });
    if (response.status >= 400) {
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return new Response(JSON.stringify({ message: "Email added to the newsletter" }), {
        status: 200
      });
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
async function registerEmail$5(email) {
  const CONVERTKIT_FORM_ID = private_env.CONVERTKIT_FORM_ID;
  const CONVERTKIT_API_KEY = private_env.CONVERTKIT_API_KEY;
  const CONVERTKIT_API_URL = private_env.CONVERTKIT_API_URL;
  try {
    const data = { email, api_key: CONVERTKIT_API_KEY };
    const response = await fetch(`${CONVERTKIT_API_URL}forms/${CONVERTKIT_FORM_ID}/subscribe`, {
      body: JSON.stringify(data),
      headers: { "Content-Type": "application/json" },
      method: "POST"
    });
    if (response.status >= 400) {
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return new Response(JSON.stringify({ message: "Email added to the newsletter" }), {
        status: 200
      });
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
async function registerEmail$4(email) {
  const EMAILOCTOPUS_API_URL = private_env.EMAILOCTOPUS_API_URL;
  const EMAILOCTOPUS_API_KEY = private_env.EMAILOCTOPUS_API_KEY;
  const EMAILOCTOPUS_LIST_ID = private_env.EMAILOCTOPUS_LIST_ID;
  try {
    const data = { email_address: email, api_key: EMAILOCTOPUS_API_KEY };
    const API_ROUTE = `${EMAILOCTOPUS_API_URL}lists/${EMAILOCTOPUS_LIST_ID}/contacts`;
    const response = await fetch(API_ROUTE, {
      body: JSON.stringify(data),
      headers: { "Content-Type": "application/json" },
      method: "POST"
    });
    if (response.status >= 400) {
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return new Response(JSON.stringify({ message: "Email added to the newsletter" }), {
        status: 200
      });
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
async function registerEmail$3(email) {
  const KLAVIYO_API_KEY = private_env.KLAVIYO_API_KEY;
  const KLAVIYO_LIST_ID = private_env.KLAVIYO_LIST_ID;
  try {
    const response = await fetch(
      `https://a.klaviyo.com/api/v2/list/${KLAVIYO_LIST_ID}/subscribe?api_key=${KLAVIYO_API_KEY}`,
      {
        method: "POST",
        headers: { Accept: "application/json", "Content-Type": "application/json" },
        // You can add additional params here i.e. SMS, etc.
        // https://developers.klaviyo.com/en/reference/subscribe
        body: JSON.stringify({ profiles: [{ email }] })
      }
    );
    if (response.status >= 400) {
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return new Response(JSON.stringify({ message: "Email added to the newsletter" }), {
        status: 200
      });
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
async function registerEmail$2(email) {
  const MAILCHIMP_DC = private_env.MAILCHIMP_DC;
  const MAILCHIMP_LIST_ID = private_env.MAILCHIMP_LIST_ID;
  const MAILCHIMP_API_KEY = private_env.MAILCHIMP_API_KEY;
  try {
    const url = `https://${MAILCHIMP_DC}.api.mailchimp.com/3.0/lists/${MAILCHIMP_LIST_ID}/members`;
    const password = MAILCHIMP_API_KEY;
    const data = { email_address: email, status: "subscribed" };
    let headers = new Headers();
    headers.append("Authorization", "Basic " + base64.encode("somestring:" + password));
    const response = await fetch(url, {
      method: "POST",
      headers,
      body: JSON.stringify(data)
    });
    const mailchimpResponse = await response.json();
    if (mailchimpResponse?.status !== "subscribed") {
      console.error(mailchimpResponse);
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return mailchimpResponse;
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
async function registerEmail$1(email) {
  const REVUE_API_KEY = private_env.REVUE_API_KEY;
  const REVUE_API_URL = private_env.REVUE_API_URL;
  const REVUE_ROUTE = `${REVUE_API_URL}subscribers`;
  try {
    const response = await fetch(REVUE_ROUTE, {
      method: "POST",
      headers: { Authorization: `Token ${REVUE_API_KEY}`, "Content-Type": "application/json" },
      body: JSON.stringify({ email, double_opt_in: false })
    });
    if (response.status >= 400) {
      throw error(400, "couldn't add email to the newsletter");
    } else {
      return new Response(JSON.stringify({ message: "Email added to the newsletter" }), {
        status: 200
      });
    }
  } catch (err) {
    console.error(err);
    throw error(err.status, err.title);
  }
}
const registerEmail = (email) => {
  switch (config.newsletter) {
    case "mailchimp":
      return registerEmail$2(email);
    case "emailoctopus":
      return registerEmail$4(email);
    case "buttondown":
      return registerEmail$6(email);
    case "convertkit":
      return registerEmail$5(email);
    case "klaviyo":
      return registerEmail$3(email);
    case "revue":
      return registerEmail$1(email);
    default:
      return;
  }
};
const POST = async ({ request }) => {
  try {
    const { email } = await request.json();
    let result = await registerEmail(email);
    return new Response(
      JSON.stringify(result, {
        status: result.status
      })
    );
  } catch (err) {
    console.error(err);
    throw error(400, "couldn't add email to the newsletter");
  }
};

export { POST };
//# sourceMappingURL=_server-6317d6d9.js.map
