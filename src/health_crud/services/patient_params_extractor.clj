(ns health-crud.services.patient-params-extractor)

(defn extract-patient [params] {:first_name (-> params
                                                :first_name)
                                :second_name (-> params
                                                 :second_name)
                                :gender (-> params
                                            :gender)
                                :birth_date (-> params
                                                :birth_date)
                                :adress (-> params
                                            :adress)
                                :chi_number (-> params
                                                :chi_number)})
