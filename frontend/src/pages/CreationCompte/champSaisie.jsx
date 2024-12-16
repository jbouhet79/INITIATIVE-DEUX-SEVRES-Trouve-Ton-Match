import { useEffect, useRef, useState, forwardRef } from "react";
import './creationCompte.css';

export const ChampSaisie = forwardRef(({setValue, label, name, value, regex}, ref) => {

    const [errMsg, setErrMsg] = useState([{}]);

    const [validInput, setValidInput] = useState(false);
    const [focusInput, setFocusInput] = useState(false);


    // const inputRef = useRef();
    useEffect(() => {
        if (ref && ref.current) {
          ref.current.focus();
        }
      }, [ref]);

    // useEffect(() => {
    //     inputRef.current.focus()
    // }, [])

     useEffect(() => {
           const result = regex.test(value);
           setValidInput(result);
       }, [value]);

    const handleChange = (e) => {
     setValue(e.target.value);
    }


    const addMsgError = (msgParam, focusSeter, validElement) => {
        focusSeter(false)
        const newMsg = validElement ? "" : msgParam;
        setErrMsg(newMsg)
    }

    return (
        <div className='row g-2 mb-3'>
                <div className="col-md">
                    <label htmlFor={name} className="form-label">{label}</label>
                    <div className="custom-container">
                        
                        <input 
                            ref={ref}
                            onChange={handleChange}
                            value={value}
                            name={name}
                            onBlur={
                                () => addMsgError('Format de saisie non respecté !', setFocusInput, validInput)
                            }
                            onFocus={() => { setFocusInput(true) }}
                            type="text"

                            className={`form-control custom-input ${!value ? "" : validInput ? "is-valid" : "is-invalid"}`}

                            disabled={false}

                            id={name}
                        />

                    </div>
                    {
                        (!validInput && value && !focusInput) && <div className="alert alert-danger mt-3" role="alert">
                            {errMsg}
                        </div>
                    }
                </div>
              </div>
    )
});

